package com.jojoldu.devbeginnernews.web.presentation;

import com.jojoldu.devbeginnernews.core.article.ArticleDetailType;
import com.jojoldu.devbeginnernews.core.common.type.EnumMapper;
import com.jojoldu.devbeginnernews.web.service.ArticleService;
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
    private final ArticleService articleService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("types", enumMapper.getOne(ArticleDetailType.class));
        model.addAttribute("articles", articleService.findAllLimitDesc(0, 20));
        return "index";
    }
}
