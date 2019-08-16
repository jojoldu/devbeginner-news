package com.jojoldu.devbeginnernews.admin;

import com.jojoldu.devbeginnernews.core.article.Article;
import com.jojoldu.devbeginnernews.core.article.ArticleRepository;
import com.jojoldu.devbeginnernews.core.article.facebook.ArticleFacebook;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
@Service
public class ArticleAdminService {
    private final ArticleRepository articleRepository;

    @Transactional
    public void saveChilds (long id) {
        log.info(">>>>>>>> saveChilds");
        Article article = articleRepository.getOne(id);
        article.setFacebook(ArticleFacebook.byApi()
                .pageId("1")
                .postsId("1")
                .build());
    }
}
