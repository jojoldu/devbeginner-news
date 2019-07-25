package com.jojoldu.devbeginnernews.web.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class index {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
