package com.example.spicydetector.spicyitem.dto;

/**
 * 기준 음식 대비 상대 맵기. 기준 음식을 100으로 환산한 값(범위).
 */
public class RelativeScore {

    public final String baseItemName;
    public final Integer scoreMin;
    public final Integer scoreMax;
    public final String text;

    public RelativeScore(String baseItemName, Integer scoreMin, Integer scoreMax, String text) {
        this.baseItemName = baseItemName;
        this.scoreMin = scoreMin;
        this.scoreMax = scoreMax;
        this.text = text;
    }
}
