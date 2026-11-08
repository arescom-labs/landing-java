package kr.arescom.landing.core.config.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpaForwardController {

    /**
     * Vue Router 관리자 경로 직접 접근 처리
     */
    @GetMapping({
        "/admin",
        "/admin/**"
    })
    public String forwardAdmin() {
        return "forward:/index.html";
    }

}