package com.example.spicydetector.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 프론트엔드 개발 서버 등에서의 교차 출처 요청을 허용하는 CORS 설정.
 * 허용 오리진은 application.yml 의 app.cors.allowed-origins 에서 관리한다.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    private final String[] allowedOrigins;

    public CorsConfig(@Value("${app.cors.allowed-origins}") String allowedOrigins) {
        // 콤마로 구분된 오리진 문자열을 배열로 변환
        this.allowedOrigins = allowedOrigins.split(",");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 프론트를 백엔드가 same-origin 으로 서빙하거나 임시 터널로 공개할 때,
        // 브라우저가 붙이는 Origin(고정 불가한 터널 도메인 등)이 차단되지 않도록 패턴 와일드카드를 사용한다.
        // 명시된 allowedOrigins(개발 서버)는 그대로 허용 목록에 포함한다.
        registry.addMapping("/api/**")
                .allowedOrigins(allowedOrigins)
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
