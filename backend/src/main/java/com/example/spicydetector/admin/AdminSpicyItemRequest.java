package com.example.spicydetector.admin;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.spicydetector.common.Category;
import com.example.spicydetector.common.ConfidenceLevel;
import com.example.spicydetector.common.SourceType;

/**
 * 관리자용 음식 등록/수정 요청.
 *
 * <p>주의: 정확한 SHU 를 모르는 항목은 공식값처럼 저장하지 않는다.
 * sourceType 은 SAMPLE/USER_ESTIMATE, confidenceLevel 은 LOW/UNKNOWN 을 권장.</p>
 */
public class AdminSpicyItemRequest {

    @NotBlank(message = "음식명은 필수입니다.")
    private String name;

    private String brand;

    @NotNull(message = "카테고리는 필수입니다.")
    private Category category;

    private Integer shuMin;
    private Integer shuMax;
    private Integer shuValue;
    private String shuText;

    private Boolean officialYn;
    private SourceType sourceType;
    private String sourceUrl;
    private String sourceNote;
    private ConfidenceLevel confidenceLevel;
    private String description;

    /** 검색 보조용 별칭 (선택) */
    private List<String> aliases;

    // ===== getters / setters =====

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

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }
}
