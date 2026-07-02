package com.example.spicydetector.spicyitem.dto;

import java.util.List;

import com.example.spicydetector.common.Category;
import com.example.spicydetector.common.ConfidenceLevel;
import com.example.spicydetector.common.SourceType;
import com.example.spicydetector.spicyitem.SpicyItem;

/**
 * 음식 상세 정보.
 */
public class SpicyItemDetail {

    public final Long id;
    public final String name;
    public final String brand;
    public final String category;
    public final String categoryLabel;
    public final Integer shuValue;
    public final Integer shuMin;
    public final Integer shuMax;
    public final String shuText;
    public final Boolean officialYn;
    public final SourceType sourceType;
    public final String sourceTypeLabel;
    public final String sourceUrl;
    public final String sourceNote;
    public final ConfidenceLevel confidenceLevel;
    /** 데이터 기준 뱃지: 공식/추정/체감/샘플 */
    public final String dataBasis;
    public final String perceivedLabel;
    public final String description;

    /** 기준 음식 대비 상대 맵기 (기준 음식이 선택된 경우) */
    public final RelativeScore relativeScore;
    /** 신라면(=100) 대비 상대 지수. 개인화 판독(내 맵력으로 먹을 수 있나)에 사용. */
    public final Integer shinRelative;
    /** 비슷한 매운맛 음식 */
    public final List<SimilarItem> similarItems;

    public SpicyItemDetail(SpicyItem item, String perceivedLabel,
                           RelativeScore relativeScore, Integer shinRelative, List<SimilarItem> similarItems) {
        this.id = item.getId();
        this.name = item.getName();
        this.brand = item.getBrand();
        Category c = item.getCategory();
        this.category = c != null ? c.name() : null;
        this.categoryLabel = c != null ? c.getLabel() : null;
        this.shuValue = item.getShuValue();
        this.shuMin = item.getShuMin();
        this.shuMax = item.getShuMax();
        this.shuText = item.getShuText();
        this.officialYn = item.getOfficialYn();
        this.sourceType = item.getSourceType();
        this.sourceTypeLabel = item.getSourceType() != null ? item.getSourceType().getLabel() : null;
        this.sourceUrl = item.getSourceUrl();
        this.sourceNote = item.getSourceNote();
        this.confidenceLevel = item.getConfidenceLevel();
        this.dataBasis = item.getSourceType() != null ? item.getSourceType().basis() : "샘플";
        this.perceivedLabel = perceivedLabel;
        this.description = item.getDescription();
        this.relativeScore = relativeScore;
        this.shinRelative = shinRelative;
        this.similarItems = similarItems;
    }
}
