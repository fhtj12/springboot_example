package com.example.web.util;

import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

public class MessageManager {

    private static final ResourceBundleMessageSource rfMessage;

    static {
        rfMessage = new ResourceBundleMessageSource();
        rfMessage.setBasename("messages");
        rfMessage.setDefaultEncoding("UTF-8");
    }

    public static String getMessage(String key) {
        return rfMessage.getMessage(key, null, Locale.getDefault());
    }

    public static String getMessage(String key, Locale locale) {
        return rfMessage.getMessage(key, null, locale);
    }

    public static String getMessage(String key, Object ... messageArgs) {
        return rfMessage.getMessage(key, messageArgs, Locale.getDefault());
    }

    public static String getMessage(String key, Object[] messageArgs, Locale locale) {
        return rfMessage.getMessage(key, messageArgs, locale);
    }

}
