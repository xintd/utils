package com.coap.netty.handler;

import com.coap.netty.util.CoAPUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.core.coap.Response;
import org.eclipse.californium.core.network.serialization.DataParser;
import org.eclipse.californium.core.network.serialization.DataSerializer;
import com.coap.netty.service.CoAPRequestService;

/**
 * netty处理CoAP数据
 *
 * @author qingyan on 2018-05-05
 */
public class CoApHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            System.out.println("进入netty 9999端口");
            // 1. 接收数据
            ByteBuf buf = (ByteBuf) msg;
            byte[] receiveByteArray = new byte[buf.readableBytes()];
            buf.readBytes(receiveByteArray);
            DataParser dataParser = new DataParser(receiveByteArray);
            if (dataParser.isRequest()) {
                System.out.println("CoAP是Request");
                // 2. 解析request
                Request request = dataParser.parseRequest();
                Response response = new CoAPRequestService().dealRequest(request);
                if (response == null) {
                    return;
                }
                // 3. 前4个字节存放response的长度, 后面的字节存放response内容
                byte[] resultByte = CoAPUtil.wrapResponse(DataSerializer.serializeResponse(response));
                ByteBuf responseBuf = Unpooled.wrappedBuffer(resultByte);
                ctx.writeAndFlush(responseBuf);
            } else if (dataParser.isResponse()) {
                System.out.println("CoAP是Response");
            }
            System.out.println("退出netty 9999端口");
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    public static void main(String[] args) {
        String str = "60:43:25:16:b6:64:65:76:69:63:65:05:6c:6f:67:69:6e:4d:1a:64:65:76:5f:69:64:3d:38:65:35:31:62:66:35:62:64:35:37:39:34:66:34:66:65:30:33:63:61:34:64:65:31:66:37:65:62:30:34:63";
        System.out.println(str.replaceAll(":", ""));
    }
}
