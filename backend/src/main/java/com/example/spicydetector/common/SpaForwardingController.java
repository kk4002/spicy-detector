package com.example.spicydetector.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 빌드된 React SPA 를 Spring Boot 가 직접 서빙할 때, 클라이언트 라우팅 경로(예: /test/result/xxx)로
 * 새로고침/직접 진입해도 index.html 로 포워딩되도록 한다.
 *
 * <p>정적 리소스(파일명에 '.' 포함)와 /api, /h2-console 는 제외한다.
 * 프론트를 빌드해 resources/static 에 넣지 않은 개발 환경에서는 index.html 이 없어 404 가 날 수 있으며,
 * 그 경우 프론트는 vite 개발서버(5173)로 접속하면 된다.</p>
 */
@Controller
public class SpaForwardingController {

    private static final String INDEX = "forward:/index.html";

    @RequestMapping(value = {
            "/",
            "/{p1:(?!api|h2-console|assets)[^\\.]+}",
            "/{p1:(?!api|h2-console|assets)[^\\.]+}/{p2:[^\\.]+}",
            "/{p1:(?!api|h2-console|assets)[^\\.]+}/{p2:[^\\.]+}/{p3:[^\\.]+}"
    })
    public String forward() {
        return INDEX;
    }
}
