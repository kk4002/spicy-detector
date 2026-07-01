package com.example.spicydetector.spicyitem;

import org.springframework.stereotype.Component;

import com.example.spicydetector.spicyitem.dto.RelativeScore;

/**
 * 상대 맵기 계산과 체감 등급 산정을 담당하는 순수 계산 유틸.
 *
 * <p>핵심 공식: relativeScore = targetShu / baseShu * 100.
 * 대상 음식이 범위(shuMin~shuMax)를 가지면 결과도 범위로 표현한다.</p>
 */
@Component
public class SpicyScoreCalculator {

    /**
     * 기준 음식(base) 대비 대상 음식(target)의 상대 맵기를 계산한다.
     * 계산이 불가능하면(기준값 없음 등) null 을 반환한다.
     */
    public RelativeScore relativeScore(SpicyItem target, SpicyItem base) {
        if (base == null) {
            return null;
        }
        Integer baseShu = base.representativeShu();
        if (baseShu == null || baseShu <= 0) {
            return null;
        }

        // 대상의 범위: 명시적 범위가 있으면 사용, 없으면 대표값으로 단일 범위
        Integer targetRep = target.representativeShu();
        Integer tMin = target.getShuMin() != null ? target.getShuMin() : targetRep;
        Integer tMax = target.getShuMax() != null ? target.getShuMax() : targetRep;
        if (tMin == null || tMax == null) {
            return null;
        }

        int scoreMin = Math.round(tMin * 100f / baseShu);
        int scoreMax = Math.round(tMax * 100f / baseShu);
        if (scoreMin > scoreMax) {
            int tmp = scoreMin;
            scoreMin = scoreMax;
            scoreMax = tmp;
        }

        String text;
        if (scoreMin == scoreMax) {
            text = String.format("%s 기준 약 %d", base.getName(), scoreMin);
        } else {
            text = String.format("%s 기준 약 %d~%d", base.getName(), scoreMin, scoreMax);
        }
        return new RelativeScore(base.getName(), scoreMin, scoreMax, text);
    }

    /**
     * 기준 음식 대비 단일 상대 지수(대표값 기준). 랭킹/비슷한 매움 비교 등에 사용.
     * 계산 불가 시 null.
     */
    public Integer relativeScoreValue(SpicyItem target, SpicyItem base) {
        if (base == null) {
            return null;
        }
        Integer baseShu = base.representativeShu();
        Integer targetShu = target.representativeShu();
        if (baseShu == null || baseShu <= 0 || targetShu == null) {
            return null;
        }
        return Math.round(targetShu * 100f / baseShu);
    }

    /**
     * 대표 스코빌 값을 사람이 읽기 쉬운 체감 등급 문구로 환산한다.
     * 수치가 없으면 "정보 부족".
     */
    public String perceivedLabel(SpicyItem item) {
        Integer shu = item.representativeShu();
        if (shu == null) {
            return "정보 부족";
        }
        if (shu < 1500) {
            return "순한맛";
        }
        if (shu < 3000) {
            return "적당히 매움";
        }
        if (shu < 5000) {
            return "꽤 매움";
        }
        if (shu < 10000) {
            return "매우 매움";
        }
        return "극악";
    }
}
