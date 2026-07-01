package com.example.spicydetector.common;

/**
 * 매운맛 수치의 신뢰도 등급.
 * <ul>
 *   <li>HIGH    : 제조사 공식값 또는 신뢰 가능한 공개 자료</li>
 *   <li>MEDIUM  : 여러 출처에서 반복 확인되는 값</li>
 *   <li>LOW     : 커뮤니티/사용자 추정값</li>
 *   <li>UNKNOWN : 수치 없음, 체감 기반</li>
 * </ul>
 */
public enum ConfidenceLevel {
    HIGH, MEDIUM, LOW, UNKNOWN
}
