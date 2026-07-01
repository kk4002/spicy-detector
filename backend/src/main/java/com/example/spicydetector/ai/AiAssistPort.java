package com.example.spicydetector.ai;

import java.util.Optional;

/**
 * AI 보조 기능의 진입 포트 (5단계 예정).
 *
 * <p>AI 는 핵심 계산(스코빌 수치/등급 확정)에는 관여하지 않는다.
 * 음식명 자동 매칭, 리뷰 요약, 자연어 질문 해석 등 보조 역할만 담당한다.</p>
 */
public interface AiAssistPort {

    /** 사용자가 입력한 자유 문자열을 표준 음식명 후보로 매칭한다. */
    Optional<String> matchFoodName(String rawInput);

    /** 리뷰 텍스트 목록을 요약한다. */
    Optional<String> summarizeReviews(java.util.List<String> reviews);
}
