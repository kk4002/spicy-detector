package com.example.spicydetector.common;

/**
 * 스코빌 수치의 출처 유형.
 * <ul>
 *   <li>OFFICIAL      : 제조사 공식 발표</li>
 *   <li>ESTIMATE      : 공개 자료 기반 추정</li>
 *   <li>USER_ESTIMATE : 사용자/커뮤니티 추정</li>
 *   <li>COMMUNITY     : 커뮤니티 후기 취합</li>
 *   <li>SAMPLE        : 초기 샘플(출처 확인 필요)</li>
 * </ul>
 */
public enum SourceType {
    OFFICIAL, ESTIMATE, USER_ESTIMATE, COMMUNITY, SAMPLE
}
