package com.example.web.view.response;

import java.util.Map;

public interface Response {

    String CALLBACK_TYPE_JSON = "JSON";
    String CALLBACK_TYPE_XML = "XML";

    void setError(boolean error);
    void setResponseCode(String responseCode);
    void setErrorType(String errorType);
    void setResponseMsg(String msgCode);
    String getErrorCode();
    String getErrorType();
    Map<String, Object> getInputInfo();
    String getUri();
    String getUserIp();
    long getElapseStart();
}
