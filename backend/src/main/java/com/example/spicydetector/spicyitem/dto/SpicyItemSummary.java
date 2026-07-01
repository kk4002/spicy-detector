package com.example.spicydetector.spicyitem.dto;

import com.example.spicydetector.common.Category;
import com.example.spicydetector.common.ConfidenceLevel;
import com.example.spicydetector.spicyitem.SpicyItem;

/**
 * 검색 결과/랭킹 등에서 사용하는 음식 요약 정보.
 */
public class SpicyItemSummary {

    public final Long id;
    public final String name;
    public final String brand;
    public final String category;
    public final String categoryLabel;
    public final Integer shuValue;
    public final String shuText;
    public final String perceivedLabel;
    public final ConfidenceLevel confidenceLevel;
    /** 데이터 기준 뱃지: 공식/추정/체감/샘플 */
    public final String dataBasis;
    /** 기준 음식 대비 상대 지수 (검색 시 기준 음식이 선택된 경우에만 채워짐) */
    public final Integer relativeScore;

    public SpicyItemSummary(SpicyItem item, String perceivedLabel, Integer relativeScore) {
        this.id = item.getId();
        this.name = item.getName();
        this.brand = item.getBrand();
        Category c = item.getCategory();
        this.category = c != null ? c.name() : null;
        this.categoryLabel = c != null ? c.getLabel() : null;
        this.shuValue = item.getShuValue();
        this.shuText = item.getShuText();
        this.perceivedLabel = perceivedLabel;
        this.confidenceLevel = item.getConfidenceLevel();
        this.dataBasis = item.getSourceType() != null ? item.getSourceType().basis() : "샘플";
        this.relativeScore = relativeScore;
    }
}
