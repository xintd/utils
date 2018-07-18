package com.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.core.network.serialization.DataParser;
import org.eclipse.californium.core.network.serialization.DataSerializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class CoapSend {
    public static void main(String[] args) {
        try {
            // 1、创建客户端Socket，指定服务器地址和端口
            Socket socket = new Socket("127.0.0.1", 9999);
//            Socket socket = new Socket("172.13.30.180", 9999);
            System.out.println("客户端启动成功");
            // 2、获取输出流，向服务器端发送信息
            Request request = new Request(CoAP.Code.GET, CoAP.Type.CON);
            request.setURI("/device/login?dev_id=8e51bf5bd5794f4fe03ca4de1f7eb04c&version=1.0.3.0419");
            request.setToken(new byte[0]);
            OutputStream os = socket.getOutputStream();
            ByteBuf buff = Unpooled.copiedBuffer(DataSerializer.serializeRequest(request));
            os.write(buff.array());
            buff.clear();
            os.flush();
            Thread.sleep(500);
            InputStream in = socket.getInputStream();
            /*
             * 采用此方法读取数据耗时50多秒
             */
            byte[] bytes = getBytes(in);
            DataParser dataParser = new DataParser(bytes);
            System.out.println("socket端接收 ：" + dataParser);
            System.out.println("socket端接收payload ：" + dataParser.parseResponse().getPayloadString());
            /*
             * 直接读取很快能返回数据
             */
//            in = new ByteArrayInputStream(bytes);
//            byte[] bytes2 = new byte[4096];
//            in.read(bytes2);
//            System.out.println("====================="+ in.read(bytes2));
//            DataParser dataParser2 = new DataParser(bytes2);
//            System.out.println("socket端接收 直接读取数据：" + dataParser2);
//            System.out.println("socket端接收payload 直接读取数据：" + dataParser2.parseResponse().getPayloadString());
            in.close();
            os.close();
            socket.close(); // 关闭Socket
        } catch (Exception e) {
            System.out.println("can not listen to:" + e);
        }
    }

    private static byte[] getBytes(InputStream in) throws IOException {

        long timeBegin = System.currentTimeMillis();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int len = 0;
        while (len == 0) {
            len = in.available();
        }
        byte[] bytes = new byte[len];
        in.read(bytes);
        outputStream.write(bytes);
        long timeEnd = System.currentTimeMillis();
        System.out.println("耗时" + (timeEnd - timeBegin) + "毫秒");
        return outputStream.toByteArray();
    }

}
