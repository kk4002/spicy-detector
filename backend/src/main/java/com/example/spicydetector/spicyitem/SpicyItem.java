package com.example.spicydetector.spicyitem;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.example.spicydetector.common.Category;
import com.example.spicydetector.common.ConfidenceLevel;
import com.example.spicydetector.common.SourceType;

/**
 * 매운 음식 데이터.
 *
 * <p>스코빌 수치는 단일값(shuValue) 또는 범위(shuMin~shuMax)로 저장할 수 있으며,
 * 정확한 수치를 알기 어려운 경우 shuText(예: "약 4,400 SHU")와 체감 기반 분류로 표시한다.
 * 모든 수치에는 출처(sourceType)와 신뢰도(confidenceLevel)를 함께 둔다.</p>
 */
@Entity
@Table(name = "spicy_item")
public class SpicyItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 100)
    private String brand;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Category category;

    /** 스코빌 범위 최소값 (범위로 표현할 때) */
    @Column(name = "shu_min")
    private Integer shuMin;

    /** 스코빌 범위 최대값 (범위로 표현할 때) */
    @Column(name = "shu_max")
    private Integer shuMax;

    /** 스코빌 대표 단일값 (상대 비교/랭킹의 기준으로 사용) */
    @Column(name = "shu_value")
    private Integer shuValue;

    /** 화면 표시용 텍스트 (예: "약 4,400 SHU", "공식값 없음") */
    @Column(name = "shu_text", length = 100)
    private String shuText;

    /** 제조사 공식값 여부 */
    @Column(name = "official_yn")
    private Boolean officialYn = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_type", length = 50)
    private SourceType sourceType;

    @Column(name = "source_url", columnDefinition = "TEXT")
    private String sourceUrl;

    @Column(name = "source_note", columnDefinition = "TEXT")
    private String sourceNote;

    @Enumerated(EnumType.STRING)
    @Column(name = "confidence_level", length = 20)
    private ConfidenceLevel confidenceLevel = ConfidenceLevel.UNKNOWN;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
        if (this.officialYn == null) {
            this.officialYn = false;
        }
        if (this.confidenceLevel == null) {
            this.confidenceLevel = ConfidenceLevel.UNKNOWN;
        }
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 상대 비교/랭킹에 사용할 대표 스코빌 값을 반환한다.
     * shuValue 가 없으면 범위의 중앙값을, 그것도 없으면 null 을 돌려준다.
     */
    public Integer representativeShu() {
        if (shuValue != null) {
            return shuValue;
        }
        if (shuMin != null && shuMax != null) {
            return (shuMin + shuMax) / 2;
        }
        if (shuMin != null) {
            return shuMin;
        }
        return shuMax;
    }

    // ===== getters / setters =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getShuMin() {
        return shuMin;
    }

    public void setShuMin(Integer shuMin) {
        this.shuMin = shuMin;
    }

    public Integer getShuMax() {
        return shuMax;
    }

    public void setShuMax(Integer shuMax) {
        this.shuMax = shuMax;
    }

    public Integer getShuValue() {
        return shuValue;
    }

    public void setShuValue(Integer shuValue) {
        this.shuValue = shuValue;
    }

    public String getShuText() {
        return shuText;
    }

    public void setShuText(String shuText) {
        this.shuText = shuText;
    }

    public Boolean getOfficialYn() {
        return officialYn;
    }

    public void setOfficialYn(Boolean officialYn) {
        this.officialYn = officialYn;
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getSourceNote() {
        return sourceNote;
    }

    public void setSourceNote(String sourceNote) {
        this.sourceNote = sourceNote;
    }

    public ConfidenceLevel getConfidenceLevel() {
        return confidenceLevel;
    }

    public void setConfidenceLevel(ConfidenceLevel confidenceLevel) {
        this.confidenceLevel = confidenceLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
