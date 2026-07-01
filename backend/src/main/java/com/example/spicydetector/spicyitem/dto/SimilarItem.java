package com.example.spicydetector.spicyitem.dto;

/**
 * 비슷한 매운맛 음식 항목. relativeScore 는 기준 음식(=100) 대비 상대 지수.
 */
public class SimilarItem {

    public final Long id;
    public final String name;
    public final String category;
    public final String categoryLabel;
    public final Integer relativeScore;

    public SimilarItem(Long id, String name, String category, String categoryLabel, Integer relativeScore) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.categoryLabel = categoryLabel;
        this.relativeScore = relativeScore;
    }
}
