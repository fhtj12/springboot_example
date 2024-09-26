package com.example.web.processor;

import com.example.web.util.CUtil;
import com.example.web.view.request.Request;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * intercept 후 전처리
 */
@Slf4j
@Component
public class PreProcessor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Request rfRequest = new Request(request);
        request.setAttribute("request_body", rfRequest);

        log.info("[ServiceCall]{}|{}|{}|{}"
                , rfRequest.getElapseStart()
                , rfRequest.getUserIp()
                , rfRequest.getUri()
                , rfRequest.getInputData()
        );
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 성공시에만 핸들링
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);

        int status = response.getStatus();
        String resultCode = status == 200 ? "Success" : "Error";

        Object attr = request.getAttribute("request_body");
        if (attr != null) {
            Request rfRequest = (Request) attr;
            log.info("[ServiceResult]{}|{}|{}ms|{}|{}|request:{}|response:{}"
                    , resultCode
                    , status
                    , CUtil.getElapseMillis(rfRequest.getElapseStart())
                    , rfRequest.getUserIp()
                    , rfRequest.getUri()
                    , rfRequest.getInputData()
                    , rfRequest.getOutputData()
            );
        } else {
            log.info("[ServiceResult]{}|{}|{}ms|{}|{}|request:{}"
                    , resultCode
                    , status
                    , CUtil.getElapseMillis(System.currentTimeMillis())
                    , Request.getClientIp(request)
                    , request.getRequestURI()
                    , request.getParameterMap()
            );
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}
