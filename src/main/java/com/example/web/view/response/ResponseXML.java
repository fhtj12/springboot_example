package com.example.web.view.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.example.web.model.ProtoMap;
import com.example.web.util.MessageManager;
import com.example.web.view.request.Request;

/**
 * 응답 모델 xml 타입
 */
@JacksonXmlRootElement(localName = "root")
public class ResponseXML extends ResponseModel implements Response {

    public ResponseXML(Request request, ProtoMap outputData) {
        super(request, outputData);
    }

    @JacksonXmlProperty(localName = "head") private final Head head = super.getHead();
    @JacksonXmlProperty(localName = "body") private final Body body = super.getBody();

    @Override
    public void setError(boolean error) {
        this.head.setError(error);
    }

    @Override
    public void setResponseCode(String responseCode) {
        this.head.setResponseCode(responseCode);
        this.head.setResponseMsg(MessageManager.getMessage(responseCode));
    }

    @Override
    public void setErrorType(String errorType) {
        this.head.setErrorType(errorType);
    }

    @Override
    public void setResponseMsg(String msgCode) {
        this.head.setResponseMsg(MessageManager.getMessage(msgCode));
    }

    @Override
    public String getErrorCode() {
        return this.head.getResponseCode();
    }

    @Override
    public String getErrorType() {
        return this.head.getErrorType();
    }

    @Override
    public ProtoMap getInputInfo() {
        return this.body.getInputInfo();
    }
}
