package com.example.spicydetector.spicytest.dto;

import java.util.List;

import com.example.spicydetector.spicytest.SpicyGrade;
import com.example.spicydetector.spicytest.SpicyTestResult;

/**
 * 맵력테스트 결과 응답. (제출 직후 / 공유 조회 공용)
 */
public class TestResultView {

    public final int level;
    public final String badgeName;
    public final String resultCode;
    public final int totalScore;
    public final String summary;
    public final String safeZone;
    public final String challengeZone;
    public final String dangerZone;
    public final List<String> recommended;
    public final List<String> avoid;
    /** 예상 맵력 범위 (신라면=100 기준) */
    public final Integer toleranceMin;
    public final Integer toleranceMax;
    public final String shareToken;
    /** 공유용 한 줄 문구 */
    public final String shareText;

    public TestResultView(SpicyGrade grade, int totalScore, String summary,
                          String shareToken, String shareText) {
        this.level = grade.level;
        this.badgeName = grade.badgeName;
        this.resultCode = grade.code;
        this.totalScore = totalScore;
        this.summary = summary;
        this.safeZone = grade.safeZone;
        this.challengeZone = grade.challengeZone;
        this.dangerZone = grade.dangerZone;
        this.recommended = grade.recommended;
        this.avoid = grade.avoid;
        this.toleranceMin = grade.toleranceMin;
        this.toleranceMax = grade.toleranceMax;
        this.shareToken = shareToken;
        this.shareText = shareText;
    }

    /**
     * 저장된 결과 + 등급 정의로부터 뷰를 복원한다. (공유 조회용)
     * 저장 스키마에 총점 컬럼이 없어 공유 조회 시 totalScore 는 0 으로 노출한다. (등급/문구로 충분)
     */
    public static TestResultView from(SpicyTestResult saved, SpicyGrade grade, String shareText) {
        return new TestResultView(grade, 0, saved.getResultSummary(), saved.getShareToken(), shareText);
    }
}
