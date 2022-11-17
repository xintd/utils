package com.coap.netty.service;

import com.coap.netty.util.CoAPUtil;
import com.google.gson.Gson;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.OptionSet;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.core.coap.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * CoAP Request处理
 *
 * @author qingyan on 2018-05-07
 */
public class CoAPRequestService {

    public Response dealRequest(Request request) {
        String uri = CoAPUtil.getUri(request);
        switch (uri) {
            case "device/login":
                return dealLogin(request);
            case "device/heart":
                return dealHeart(request);
            default:
                break;
        }
        return null;
    }

    /**
     * 设备心跳
     * @param request CoAP Request
     * @return
     */
    private Response dealHeart(Request request) {
        Response response = CoAPUtil.buildResponse(request, CoAP.ResponseCode.VALID);
        OptionSet optionSet = CoAPUtil.buildOptionSet(request);
        response.setOptions(optionSet);
        return response;
    }

    /**
     * 设备登录
     * @param request CoAP Request
     * @return CoAP Response
     */
    private Response dealLogin(Request request) {
        Response response = CoAPUtil.buildResponse(request, CoAP.ResponseCode.VALID);
        OptionSet optionSet = CoAPUtil.buildOptionSet(request);
        response.setOptions(optionSet);
        //登录返回当前时间用于中控时间同步
        Map<String, String> params=new HashMap<>(1);
//        params.put("date", DateUtils.Date2String(new Date(),"yyyy-MM-dd HH:mm:ss"));
        response.setPayload(new Gson().toJson(params));
        return response;
    }
}
