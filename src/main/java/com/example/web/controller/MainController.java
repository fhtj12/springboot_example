package com.example.web.controller;

import com.example.web.service.InnerService;
import com.example.web.view.request.Request;
import com.example.web.view.response.Response;
import com.example.web.view.response.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "")
@RestController
public class MainController {

    private InnerService innerService;

    @Autowired
    public void setInnerService(InnerService innerService) {
        this.innerService = innerService;
    }

    @RequestMapping(value = "/")
    public org.springframework.http.ResponseEntity<String> responseDefault() {
        return new org.springframework.http.ResponseEntity<>("error", HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "InnerService/test.do")
    public org.springframework.http.ResponseEntity<Response> getTestCode(
            @RequestAttribute(value = "request_body") Request requestBody) {
        return new ResponseEntity().response(requestBody, innerService.getTestCode());
    }

}
