package com.example.web.exception;

import com.example.web.util.MessageManager;
import lombok.Getter;

import java.util.Locale;

@Getter
public class ServiceException extends RuntimeException {

    protected String errorCode;
    protected String errorMessage;
    protected Object[] errorArguments;
    protected Exception originalException;

    public ServiceException() {}

    public ServiceException(String errorCode) {
        this(errorCode, MessageManager.getMessage(errorCode));
    }

    public ServiceException(String errorCode, Locale locale) {
        this(errorCode, MessageManager.getMessage(errorCode, locale));
    }

    public ServiceException(String errorCode, Object[] errorMessage) {
        this(errorCode, MessageManager.getMessage(errorCode, errorMessage));
        this.errorArguments = errorMessage;
    }

    public ServiceException(String errorCode, Object[] errorMessage, Locale locale) {
        this(errorCode, MessageManager.getMessage(errorCode, errorMessage, locale));
        this.errorArguments = errorMessage;
    }

    public ServiceException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ServiceException(String errorCode, String errorMessage, Exception originalException) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.originalException = originalException;
    }

    public ServiceException(String errorMessage, Exception originalException) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.originalException = originalException;
    }

}
