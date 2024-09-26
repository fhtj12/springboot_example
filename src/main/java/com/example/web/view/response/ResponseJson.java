package com.example.web.view.response;

import com.example.web.model.ProtoMap;
import com.example.web.view.request.Request;
import lombok.Data;
import lombok.Getter;

@Getter
public class ResponseJson extends ResponseModel implements Response {

    public ResponseJson(Request request, ProtoMap outputData) {
        super(request, outputData);
        this.root = new Root(super.getHead(), super.getBody());
    }

    private final Root root;

    @Data
    private static class Root {
        private Head head;
        private Body body;

        public Root(Head head, Body body) {
            this.head = head;
            this.body = body;
        }
    }

}
