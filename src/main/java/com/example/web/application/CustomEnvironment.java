package com.example.web.application;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@Component
public class CustomEnvironment {

    public static final String ENV_PRODUCTION = "prod";
    public static final String ENV_DEVELOPMENT = "dev";
    public static final String ENV_LOCAL = "local";

    @Getter
    private static final String ENV;
    @Getter
    private static final String hostName;

    static {
        try {
            ENV = System.getProperty("spring.profiles.active");
            if (!isValidEnv()) {
                throw new RuntimeException("profile 값이 없거나 올바르지 않습니다. 로컬 개발의 경우 Active profiles 를 설정하십시오.");
            }

            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isValidEnv() {
        return ENV_PRODUCTION.equalsIgnoreCase(ENV) || ENV_DEVELOPMENT.equalsIgnoreCase(ENV)
                || ENV_LOCAL.equalsIgnoreCase(ENV);
    }

    public static boolean isProd() {
        return ENV_PRODUCTION.equalsIgnoreCase(ENV);
    }

    public static boolean isDev() {
        return ENV_DEVELOPMENT.equalsIgnoreCase(ENV);
    }

    public static boolean isLocal() {
        return ENV_LOCAL.equalsIgnoreCase(ENV);
    }

}
