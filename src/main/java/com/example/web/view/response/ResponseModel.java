package com.example.web.view.response;

import com.example.web.model.ProtoMap;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.web.util.CUtil;
import com.example.web.util.MessageManager;
import com.example.web.view.request.Request;
import lombok.Data;
import lombok.Getter;

/**
 * response model 객체의 super class 에서의 get 메서드 혹은 멤버들은 json ignore 필수.
 * lombok getter 쓰면 안됨.
 */
@Getter
public class ResponseModel implements Response {

    public ResponseModel(Request request, ProtoMap outputData) {
        this.head = new Head(request);
        this.body = new Body(request, outputData);
    }

    @JsonIgnore
    private final Head head;
    @JsonIgnore
    private final Body body;

    @Data
    static class Head {
        public Head(Request request) {
            this.uri = request.getUri();
            this.userIp = request.getUserIp();
            this.requestTime = request.getRequestTime();
            this.elapseStart = request.getElapseStart();
        }

        // common response
        private String uri;
        private String userIp;
        private boolean error = false;
        private String errorType = null;
        private String requestTime;
        private String responseTime = CUtil.getSimpleDate(null);
        private long elapseStart;
        private String responseCode = "RFCM0000";
        private String responseMsg = MessageManager.getMessage(responseCode);
    }

    static class Body extends ProtoMap {
        public Body(Request request, ProtoMap outputData) {
            super(outputData);
            super.put("InputInfo", request.getInputData());
        }

        @JsonIgnore
        public ProtoMap getInputInfo() {
            return (ProtoMap) super.get("InputInfo");
        }
    }

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

    @JsonIgnore
    @Override
    public String getErrorCode() {
        return this.head.responseCode;
    }

    @JsonIgnore
    @Override
    public String getErrorType() {
        return this.head.errorType;
    }

    @JsonIgnore
    @Override
    public ProtoMap getInputInfo() {
        return this.body.getInputInfo();
    }

    @JsonIgnore
    @Override
    public String getUri() {
        return this.head.uri;
    }

    @JsonIgnore
    @Override
    public String getUserIp() {
        return this.head.userIp;
    }

    @Override
    public long getElapseStart() {
        return this.head.elapseStart;
    }

}
