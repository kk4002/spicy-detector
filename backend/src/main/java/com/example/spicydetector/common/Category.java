package com.example.spicydetector.common;

/**
 * 음식 카테고리.
 */
public enum Category {
    RAMEN("라면"),
    TTEOKBOKKI("떡볶이"),
    TONKATSU("돈가스"),
    HOTSAUCE("핫소스"),
    PEPPER("고추"),
    SNACK("과자/스낵"),
    ETC("기타 음식");

    private final String label;

    Category(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
