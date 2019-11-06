package com.jojoldu.devbeginnernews.admin.web;

import com.jojoldu.devbeginnernews.admin.config.auth.LoginUser;
import com.jojoldu.devbeginnernews.admin.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        return "index";
    }
}
