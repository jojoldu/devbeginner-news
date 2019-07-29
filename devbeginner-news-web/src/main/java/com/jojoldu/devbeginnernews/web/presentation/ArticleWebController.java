package com.jojoldu.devbeginnernews.web.presentation;

import com.jojoldu.devbeginnernews.web.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ArticleWebController {
    private final ArticleService articleService;

    @GetMapping("/article/{articleId}")
    public String article (@PathVariable(value = "articleId") Long articleId, Model model) {
        model.addAttribute("article", articleService.findOne(articleId));
        return "article";
    }
}
