package com.example.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    // CORS Setting
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**") // 이 cors 설정을 모든 api 엔드포인트(/**)에 적용함.
                .allowedOrigins("http://localhost:8081") // 설정한 프론트 서버 주소에서 오는 요청만 허용함. 다른 주소로 접근시 차단한다.
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드 목록을 지정한다.
                .allowCredentials(true) // JWT 인증에서 핵심적인 설정. 인증 정보를 포함한 요청을 허용한다는 의미이다
                .allowedHeaders("*")
                .exposedHeaders("Set-Cookie", "Authorization"); // 프론트 단에서 헤더에 접근 가능하도록 설정하는 부분.
    }

}