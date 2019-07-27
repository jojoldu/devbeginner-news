package com.jojoldu.devbeginnernews.web.presentation;

import com.jojoldu.devbeginnernews.core.article.ArticleType;
import com.jojoldu.devbeginnernews.core.common.type.EnumMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class IndexController {

    private final EnumMapper enumMapper;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("types", enumMapper.getOne(ArticleType.class));
        return "index";
    }
}
