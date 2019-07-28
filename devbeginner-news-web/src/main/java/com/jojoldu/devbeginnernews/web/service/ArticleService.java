package com.jojoldu.devbeginnernews.web.service;

import com.jojoldu.devbeginnernews.core.article.ArticleRepository;
import com.jojoldu.devbeginnernews.web.repository.ArticleWebRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleWebRepository articleWebRepository;

    @Transactional(readOnly = true)
    public List<ArticleItemDto> findAllLimitDesc (long offset, long limit) {
        return articleWebRepository.findAllLimitDesc(offset, limit).stream()
                .map(ArticleItemDto::new)
                .collect(Collectors.toList());
    }
}
