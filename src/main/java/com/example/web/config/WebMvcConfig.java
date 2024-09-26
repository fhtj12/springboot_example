package com.example.web.config;

import com.example.web.processor.PreProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * spring configuration
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * multipart 파일 업로드 설정
     */
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("UTF-8");
        commonsMultipartResolver.setMaxUploadSizePerFile(10 * 1024 * 1024);
        commonsMultipartResolver.setMaxUploadSize(30 * 1024 * 1024);
        return commonsMultipartResolver;
    }

    /**
     * 전처리기 interceptor 로 추가.
     */
    private PreProcessor preProcessor;

    @Autowired
    public void setPreProcessor(PreProcessor preProcessor) {
        this.preProcessor = preProcessor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(preProcessor).addPathPatterns("/**/*.do");
    }
}
