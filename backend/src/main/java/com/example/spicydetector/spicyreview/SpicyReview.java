package com.example.spicydetector.spicyreview;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

/**
 * 사용자 체감 리뷰. (4단계에서 체감 평균 반영에 사용 — 엔티티 선정의)
 */
@Entity
@Table(name = "spicy_review")
public class SpicyReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_id", nullable = false)
    private Long itemId;

    /** 작성자의 매운맛 내성 수준 (예: LOW/MEDIUM/HIGH) */
    @Column(name = "user_tolerance", length = 30)
    private String userTolerance;

    /** 체감 매운 정도 (1~5) */
    @Column(name = "perceived_level")
    private Integer perceivedLevel;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getUserTolerance() {
        return userTolerance;
    }

    public void setUserTolerance(String userTolerance) {
        this.userTolerance = userTolerance;
    }

    public Integer getPerceivedLevel() {
        return perceivedLevel;
    }

    public void setPerceivedLevel(Integer perceivedLevel) {
        this.perceivedLevel = perceivedLevel;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
