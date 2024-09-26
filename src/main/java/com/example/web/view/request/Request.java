package com.example.web.view.request;

import com.example.web.model.ProtoMap;
import com.example.web.util.CUtil;
import com.example.web.view.response.Response;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;

/**
 * 요청 모델
 */
@Getter
public class Request {

    private final String uri;
    private final String userIp;
    private final String requestTime;
    private final long elapseStart;
    private final String callback;
    private final ProtoMap inputData;

    @Setter
    private ProtoMap outputData;

    public Request(HttpServletRequest request) {
        ProtoMap inputData = new ProtoMap();
        for (String key : request.getParameterMap().keySet()) {
            inputData.put(key, request.getParameterMap().get(key)[0]);
        }

        this.inputData = inputData;
        this.uri = request.getRequestURI();
        this.requestTime = CUtil.getSimpleDate();
        this.elapseStart = System.nanoTime(); // 업무 시작 시간 설정

        // callback type set
        String callback = String.valueOf(inputData.get("callback"));
        if (CUtil.isEmpty(callback) || "null".equalsIgnoreCase(callback)) {
            this.callback = Response.CALLBACK_TYPE_JSON; // default
        } else {
            this.callback = callback;
        }

        // ip set
        this.userIp = getClientIp(request);
    }

    public static String getClientIp(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null || "unknown".equalsIgnoreCase(clientIp)) {
            //Proxy 서버인 경우
            clientIp = request.getHeader("Proxy-Client-IP");
        }
        if (clientIp == null || "unknown".equalsIgnoreCase(clientIp)) {
            //Weblogic 서버인 경우
            clientIp = request.getHeader("WL-Proxy-Client-IP");
        }
        if (clientIp == null || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("HTTP_CLIENT_IP");
        }
        if (clientIp == null || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (clientIp == null || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getRemoteAddr();
        }

        return clientIp;
    }

}
