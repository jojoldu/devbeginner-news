package com.jojoldu.devbeginnernews.core.article.count;

import com.jojoldu.devbeginnernews.core.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 이후에 Redis나 로컬 캐시로 변경 예정 (조회수 write/update가 너무 잦아지면)
 */

@Getter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uni_article_count_1", columnNames = {"articleId"})
})
public class ArticleCount extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long articleId;

    public long count;

    @Builder
    public ArticleCount(Long articleId, long count) {
        this.articleId = articleId;
        this.count = count;
    }

    public static ArticleCount newInstance(Long articleId) {
        return ArticleCount.builder()
                .articleId(articleId)
                .count(1)
                .build();
    }

    public void increaseCount() {
        this.count += 1;
    }
}
