package com.example.spicydetector.ai;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

/**
 * AI 미연동 상태의 기본 구현. 초기에는 AI 없이도 서비스가 동작하도록 항상 빈 결과를 반환한다.
 * 실제 AI 연동 시 이 구현을 대체하거나 @Primary 빈으로 교체한다.
 */
@Component
public class NoopAiAssist implements AiAssistPort {

    @Override
    public Optional<String> matchFoodName(String rawInput) {
        return Optional.empty();
    }

    @Override
    public Optional<String> summarizeReviews(List<String> reviews) {
        return Optional.empty();
    }
}
