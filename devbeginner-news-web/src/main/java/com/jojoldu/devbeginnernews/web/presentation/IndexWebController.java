package com.jojoldu.devbeginnernews.web.presentation;

import com.jojoldu.devbeginnernews.web.service.ArticleService;
import com.jojoldu.devbeginnernews.web.service.dto.ArticleViewItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static com.jojoldu.devbeginnernews.web.config.PagingConstant.PAGE_SIZE;

@Slf4j
@RequiredArgsConstructor
@Controller
public class IndexWebController {

    private final ArticleService articleService;

    @GetMapping("/")
    public String index(Model model) {
        int offset = 0;
        List<ArticleViewItem> items = articleService.findAllLimitDesc(offset, PAGE_SIZE);
        model.addAttribute("articles", items);
        model.addAttribute("offset", offset+items.size());
        return "index";
    }

    @GetMapping("/hot")
    public String hot(Model model) {
        int offset = 0;
        List<ArticleViewItem> items = articleService.findAllMostLikes(offset, PAGE_SIZE);
        model.addAttribute("articles", items);
        model.addAttribute("offset", offset+items.size());
        return "hot";
    }

    @GetMapping("/recommended")
    public String recommended(Model model) {
        int offset = 0;
        List<ArticleViewItem> items = articleService.findAllMostLikes(offset, PAGE_SIZE);
        model.addAttribute("articles", items);
        model.addAttribute("offset", offset+items.size());
        return "hot";
    }

    @GetMapping("/signin")
    public String signin (Model model) {
        return "signin";
    }
}
