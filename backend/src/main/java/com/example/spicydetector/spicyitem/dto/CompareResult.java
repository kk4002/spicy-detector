package com.example.spicydetector.spicyitem.dto;

/**
 * 음식 vs 음식 대결 결과.
 */
public class CompareResult {

    public final SpicyItemSummary a;
    public final SpicyItemSummary b;
    /** 더 매운 쪽 이름 (비교 불가 시 null) */
    public final String spicierName;
    /** 덜 매운 쪽 이름 */
    public final String milderName;
    /** 몇 배 매운지 (예: 1.3) */
    public final Double multiple;
    /** 화면 표시용 한 줄 결과 */
    public final String text;

    public CompareResult(SpicyItemSummary a, SpicyItemSummary b,
                         String spicierName, String milderName, Double multiple, String text) {
        this.a = a;
        this.b = b;
        this.spicierName = spicierName;
        this.milderName = milderName;
        this.multiple = multiple;
        this.text = text;
    }
}
