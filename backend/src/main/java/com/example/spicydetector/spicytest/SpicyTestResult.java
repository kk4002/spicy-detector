package com.example.spicydetector.spicytest;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

/**
 * 맵력테스트 결과. 공유 토큰으로 나중에 다시 조회할 수 있다.
 */
@Entity
@Table(name = "spicy_test_result")
public class SpicyTestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 결과 식별 코드 (등급 코드 등) */
    @Column(name = "result_code", nullable = false, length = 100)
    private String resultCode;

    @Column(nullable = false)
    private Integer level;

    @Column(name = "badge_name", nullable = false, length = 100)
    private String badgeName;

    /** 기준 음식 id (선택적) */
    @Column(name = "base_item_id")
    private Long baseItemId;

    /** 예상 맵력 하한 (기준음식=100 환산) */
    @Column(name = "tolerance_min")
    private Integer toleranceMin;

    /** 예상 맵력 상한 (기준음식=100 환산) */
    @Column(name = "tolerance_max")
    private Integer toleranceMax;

    @Column(name = "result_summary", columnDefinition = "TEXT")
    private String resultSummary;

    @Column(name = "share_token", length = 100, unique = true)
    private String shareToken;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getBadgeName() {
        return badgeName;
    }

    public void setBadgeName(String badgeName) {
        this.badgeName = badgeName;
    }

    public Long getBaseItemId() {
        return baseItemId;
    }

    public void setBaseItemId(Long baseItemId) {
        this.baseItemId = baseItemId;
    }

    public Integer getToleranceMin() {
        return toleranceMin;
    }

    public void setToleranceMin(Integer toleranceMin) {
        this.toleranceMin = toleranceMin;
    }

    public Integer getToleranceMax() {
        return toleranceMax;
    }

    public void setToleranceMax(Integer toleranceMax) {
        this.toleranceMax = toleranceMax;
    }

    public String getResultSummary() {
        return resultSummary;
    }

    public void setResultSummary(String resultSummary) {
        this.resultSummary = resultSummary;
    }

    public String getShareToken() {
        return shareToken;
    }

    public void setShareToken(String shareToken) {
        this.shareToken = shareToken;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
