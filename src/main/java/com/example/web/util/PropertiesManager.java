package com.example.web.util;

import com.example.web.application.CustomEnvironment;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * bean 이 아닌 곳, 혹은 아직 initial 되지 않은 곳에서 properties 를 사용하기 위한 클래스.
 */
public class PropertiesManager {

    private static final Properties properties;

    static  {
        try {
            InputStream fis = null;
            try {
                properties = new Properties();
                fis = PropertiesManager.class.getClassLoader().getResourceAsStream("application.properties");
                properties.load(fis);

                // 실섭 환경일 경우 프로퍼티 merge.
                Properties properties_temp = new Properties();
                if (CustomEnvironment.isProd()) {
                    fis = PropertiesManager.class.getClassLoader().getResourceAsStream("application-prod.properties");
                } else if (CustomEnvironment.isDev()) {
                    fis = PropertiesManager.class.getClassLoader().getResourceAsStream("application-dev.properties");
                } else {
                    fis = PropertiesManager.class.getClassLoader().getResourceAsStream("application-local.properties");
                }
                properties_temp.load(fis);
                properties.putAll(properties_temp);
            } finally {
                if (fis != null) {
                    fis.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

}
