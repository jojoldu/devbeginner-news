package com.jojoldu.devbeginnernews.web.presentation;

import com.jojoldu.devbeginnernews.web.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static com.jojoldu.devbeginnernews.web.config.PagingConstant.PAGE_SIZE;

@Slf4j
@RequiredArgsConstructor
@Controller
public class IndexWebController {

    private final ArticleService articleService;

    @GetMapping("/")
    public String index(Model model) {
        int offset = 0;
        model.addAttribute("articles", articleService.findAllLimitDesc(offset, PAGE_SIZE));
        model.addAttribute("offset", offset+1);
        return "index";
    }
}
