package com.example.bookreviewclient.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class ImageController implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // TODO Auto-generated method stub
        registry
                // 이미지 파일의 요청 경로를 지정한다.
                .addResourceHandler("/images/**")
                // 이미지 파일을 불러올 로컬 저장소의 위치를 지정한다.
                .addResourceLocations("file:/c:/Users/sy_park/Ideal/book-review/src/main/resources/static/editorUpload/");
    }
}