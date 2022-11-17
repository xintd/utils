package com.coap.netty.util;

import com.common.utils.CollectionUtils;
import com.common.utils.StringUtils;
import com.google.common.base.Preconditions;
import org.eclipse.californium.core.coap.*;

import java.util.List;

/**
 * CoAP工具类
 *
 * @author qingyan on 2018-05-07
 */
public class CoAPUtil {

    /**
     * 创建Response
     *
     * @param request CoAP Request
     * @param responseCode CoAP响应码
     * @return CoAP Response
     */
    public static Response buildResponse(Request request, CoAP.ResponseCode responseCode) {
        Response resp = new Response(responseCode);
        resp.setType(CoAP.Type.ACK);
        resp.setMID(request.getMID());
        resp.setToken(request.getToken());
        return resp;
    }

    /**
     * 获取中控携带的deviceId
     *
     * @param request CoAP Request
     * @return deviceId
     */
    public static String getDeviceID(Request request) {
        Preconditions.checkArgument(null != request, "request不能为空");
        if (request.getOptions() == null) {
            return "";
        }
        OptionSet sets = request.getOptions();
        return getDeviceIDFromOptionSet(sets);
    }

    /**
     * 获取中控请求的uri地址
     *
     * @param request CoAP Request
     * @return uri地址
     */
    public static String getUri(Request request) {
        String uri = "";
        if (request.getOptions() != null) {
            uri = request.getOptions().getUriPathString();
        }
        return uri;
    }

    /**
     * 创建中控需要的dev_id
     *
     * @param request CoAP Request
     * @return OptionSet
     */
    public static OptionSet buildOptionSet(Request request) {
        String deviceId = CoAPUtil.getDeviceID(request);
        OptionSet optionSet = new OptionSet();
        // 这里不需要返回uri_path给中控, 字节长度才能和接入层一样.
//        optionSet.setUriPath(request.getOptions().getUriPathString());
        optionSet.setUriQuery("dev_id=" + deviceId);
        return optionSet;
    }

    public static byte[] addBytes(byte[] data1, byte[] data2) {
        byte[] data3 = new byte[data1.length + data2.length];
        System.arraycopy(data1, 0, data3, 0, data1.length);
        System.arraycopy(data2, 0, data3, data1.length, data2.length);
        return data3;
    }

    /**
     * 将int数值转换为占四个字节的byte数组<br>
     * 低位在后，高位在前
     *
     * @author qingyan
     * @param value
     *            要转换的int值
     * @return byte数组
     */
    public static byte[] intToBytes( int value ) {
        byte[] src = new byte[4];
        src[0] =  (byte) ((value>>24) & 0xFF);
        src[1] =  (byte) ((value>>16) & 0xFF);
        src[2] =  (byte) ((value>>8) & 0xFF);
        src[3] =  (byte) (value & 0xFF);
        return src;
    }

    /**
     * 包装CoAP Response<br>
     * 前4个字节存放Response的长度
     *
     * @author qingyan
     * @param responseBytes CoAP Response字节
     * @return 包装4个字节长度后的CoAP Response字节
     */
    public static byte[] wrapResponse(byte[] responseBytes) {
        byte[] lengthData = intToBytes(responseBytes.length);
        byte[] resultData = new byte[lengthData.length + responseBytes.length];
        System.arraycopy(lengthData, 0, resultData, 0, lengthData.length);
        System.arraycopy(responseBytes, 0, resultData, lengthData.length, responseBytes.length);
        return resultData;
    }

    private static String getDeviceIDFromOptionSet(OptionSet sets) {
        Preconditions.checkArgument(null != sets, "sets.getOptions()不能为空");
        Preconditions.checkArgument(null != sets.getUriQuery(), "sets.getOptions().getUriQuery()不能为空");

        String deviceID = "";

        for (String param : sets.getUriQuery())
        {
            if (param.contains("dev_id"))
            {
                List stringList = StringUtils.split2List(param, "=");
                if ((CollectionUtils.isNotNullOrEmpty(stringList)) && (stringList.size() >= 2))
                {
                    deviceID = (String)stringList.get(1);
                }
            }
        }
        return deviceID;
    }
}
