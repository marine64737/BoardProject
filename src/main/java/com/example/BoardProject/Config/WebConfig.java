package com.example.BoardProject.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) { // 파일 업로드 관련 함수, 이해가 불확실하여 추후 재학습
        registry.addResourceHandler("/js/**") //파일
                .addResourceLocations("file:"+System.getProperty("user.dir")+"/src/main/resources/static/js");
        registry.addResourceHandler("/js/**") //파일
                .addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/upload/**") //파일
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/upload/");
    }
}

