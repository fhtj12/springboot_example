package com.example.web.view.response;

import com.example.web.exception.ServiceException;
import com.example.web.model.ProtoMap;
import com.example.web.view.request.Request;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Slf4j
public class ResponseEntity {

    private final HttpHeaders httpHeaders = new HttpHeaders();

    public org.springframework.http.ResponseEntity<Response> response(Request request, ProtoMap outputData) throws ServiceException {
        setHeaders(request);
        request.setOutputData(outputData);
        return makeResponseEntity(parseResponse(request, outputData));
    }

    public Response responseError(Request request) throws ServiceException {
        setHeaders(request);
        return parseResponse(request, null);
    }

    private Response parseResponse(Request request, ProtoMap outputData) {
        if (outputData == null || outputData.isEmpty()) {
            outputData = new ProtoMap();
        }

        if (Response.CALLBACK_TYPE_XML.equalsIgnoreCase(request.getCallback())) {
            this.httpHeaders.setContentType(MediaType.APPLICATION_XML);
            return new ResponseXML(request, outputData);
        } else { // default : json
            this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseJson(request, outputData);
        }
    }

    private org.springframework.http.ResponseEntity<Response> makeResponseEntity(Response response) {
        return org.springframework.http.ResponseEntity.ok()
                .headers(httpHeaders)
                .body(response);
    }

    private void setHeaders(Request request) {
        this.httpHeaders.set("uri", request.getUri());
        this.httpHeaders.set("callback", request.getCallback());
        this.httpHeaders.set("_ELAPSE_START", String.valueOf(request.getElapseStart()));
    }

}
