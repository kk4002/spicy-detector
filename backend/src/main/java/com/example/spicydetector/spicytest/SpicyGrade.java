package com.example.spicydetector.spicytest;

import java.util.List;

/**
 * 맵력 등급 정의(불변). 총점 구간에 매핑되며, 각 등급별 안내 문구를 담는다.
 * tolerance* 는 "신라면=100" 기준으로 환산한 예상 맵력 범위이다.
 */
public class SpicyGrade {

    public final int level;
    public final String code;
    public final String badgeName;
    public final int minScore;      // 포함
    public final int maxScore;      // 포함
    public final int toleranceMin;  // 신라면=100 기준
    public final int toleranceMax;
    public final String safeZone;
    public final String challengeZone;
    public final String dangerZone;
    public final List<String> recommended;
    public final List<String> avoid;

    public SpicyGrade(int level, String code, String badgeName, int minScore, int maxScore,
                      int toleranceMin, int toleranceMax,
                      String safeZone, String challengeZone, String dangerZone,
                      List<String> recommended, List<String> avoid) {
        this.level = level;
        this.code = code;
        this.badgeName = badgeName;
        this.minScore = minScore;
        this.maxScore = maxScore;
        this.toleranceMin = toleranceMin;
        this.toleranceMax = toleranceMax;
        this.safeZone = safeZone;
        this.challengeZone = challengeZone;
        this.dangerZone = dangerZone;
        this.recommended = recommended;
        this.avoid = avoid;
    }

    public boolean contains(int score) {
        return score >= minScore && score <= maxScore;
    }
}
