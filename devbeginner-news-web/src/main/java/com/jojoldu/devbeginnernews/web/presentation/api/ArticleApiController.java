package com.jojoldu.devbeginnernews.web.presentation.api;

import com.jojoldu.devbeginnernews.web.config.handlebars.HandlebarsFactory;
import com.jojoldu.devbeginnernews.web.config.handlebars.HandlebarsTemplates;
import com.jojoldu.devbeginnernews.web.presentation.api.dto.WebResponseDto;
import com.jojoldu.devbeginnernews.web.presentation.api.dto.WebResponseStatus;
import com.jojoldu.devbeginnernews.web.service.ArticleService;
import com.jojoldu.devbeginnernews.web.service.dto.ArticleViewItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jojoldu.devbeginnernews.web.config.PagingConstant.PAGE_SIZE;
import static com.jojoldu.devbeginnernews.web.presentation.api.dto.WebResponseStatus.OK;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ArticleApiController {

    private final ArticleService articleService;
    private final HandlebarsFactory handlebarsFactory;

    @GetMapping("/api/v1/articles/{offset}")
    public WebResponseDto<String> readMore (@PathVariable(value = "offset") Long offset) {
        Map<String, Object> templateData = new HashMap<>();
        List<ArticleViewItem> items = articleService.findAllLimitDesc(offset, PAGE_SIZE);
        templateData.put("articles", items);
        templateData.put("offset", offset+items.size());

        return new WebResponseDto<>(OK, handlebarsFactory.template(HandlebarsTemplates.ARTICLES.getFileName(), templateData));
    }
}
