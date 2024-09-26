package com.example.web.processor;

import com.example.web.exception.ServiceException;
import com.example.web.util.CUtil;
import com.example.web.view.request.Request;
import com.example.web.view.response.Response;
import com.example.web.view.response.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * exception 발생시 응답 전에 핸들링하는 클래스.
 */
@Slf4j
@RestControllerAdvice
public class RFExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public @ResponseBody
    Response handleServiceException(ServiceException se, HttpServletRequest request) {
        Request rfRequest = extractRequestBody(request);
        Response response = new ResponseEntity().responseError(rfRequest);
        response.setError(true);
        response.setResponseCode(se.getErrorCode());
        response.setErrorType("SYSTEM");
        logError(se, response);
        return response;
    }

    @ExceptionHandler(Exception.class)
    public @ResponseBody
    Response handleException(Exception e, HttpServletRequest request) {
        Request rfRequest = extractRequestBody(request);
        Response response = new ResponseEntity().responseError(rfRequest);
        response.setError(true);
        response.setResponseCode("RFCM9999");
        response.setErrorType("SYSTEM");
        response.setResponseMsg("RFCM9999");
        logError(e, response);
        return response;
    }

    private Request extractRequestBody(HttpServletRequest request) {
        Object attribute = request.getAttribute("request_body");
        if (attribute == null) {
            return new Request(request);
        } else {
            return (Request) attribute;
        }
    }

    private void logError(Exception e, Response response) {
        log.info("[ServiceResult]Error|{}|{}ms|{}|{}|{}|{}|{}|{}"
                , 500
                , CUtil.getElapseMillis(response.getElapseStart())
                , response.getUserIp()
                , response.getUri()
                , response.getInputInfo()
                , response.getErrorCode()
                , response.getErrorType()
                , "" + e.getCause()
        );
    }

}
