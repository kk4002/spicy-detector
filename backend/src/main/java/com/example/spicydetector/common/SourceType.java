package com.example.spicydetector.common;

/**
 * 스코빌 수치의 출처 유형. 신뢰도 순으로 세분화한다.
 * <ul>
 *   <li>OFFICIAL_MANUFACTURER : 제조사 공식 자료/보도자료</li>
 *   <li>OFFICIAL_LAB          : 시험기관/연구기관/HPLC 측정 자료</li>
 *   <li>PUBLIC_RESEARCH       : 논문/학술 자료</li>
 *   <li>NEWS                  : 언론 보도</li>
 *   <li>REFERENCE_SITE        : 위키/전문 정보 사이트 등 참고 자료</li>
 *   <li>COMMUNITY_ESTIMATE    : 커뮤니티 추정</li>
 *   <li>USER_PERCEIVED        : 사용자 체감(후기/투표) 기반</li>
 *   <li>SAMPLE                : 개발용 샘플 데이터</li>
 *   <li>UNKNOWN               : 출처 불명 / 공식 수치 없음</li>
 * </ul>
 */
public enum SourceType {
    OFFICIAL_MANUFACTURER("제조사 공식"),
    OFFICIAL_LAB("시험기관 측정"),
    PUBLIC_RESEARCH("논문/학술"),
    NEWS("언론 보도"),
    REFERENCE_SITE("참고 정보 사이트"),
    COMMUNITY_ESTIMATE("커뮤니티 추정"),
    USER_PERCEIVED("사용자 체감"),
    SAMPLE("개발용 샘플"),
    UNKNOWN("출처 불명");

    private final String label;

    SourceType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
