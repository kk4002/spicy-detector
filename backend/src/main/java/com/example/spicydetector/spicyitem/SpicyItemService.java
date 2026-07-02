package com.example.spicydetector.spicyitem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.spicydetector.common.Category;
import com.example.spicydetector.common.NotFoundException;
import com.example.spicydetector.spicyitem.dto.CompareResult;
import com.example.spicydetector.spicyitem.dto.NeighborItem;
import com.example.spicydetector.spicyitem.dto.RelativeScore;
import com.example.spicydetector.spicyitem.dto.SimilarItem;
import com.example.spicydetector.spicyitem.dto.SpicyItemDetail;
import com.example.spicydetector.spicyitem.dto.SpicyItemSummary;

/**
 * 음식 데이터 조회/검색/상세/비슷한 매움/랭킹 도메인 서비스.
 */
@Service
@Transactional(readOnly = true)
public class SpicyItemService {

    /** 비슷한 매움 판정 기본 허용 범위 (기준=100 대비 ±20) */
    private static final int DEFAULT_SIMILAR_RANGE = 20;
    /** 상세 화면에 노출할 비슷한 음식 최대 개수 */
    private static final int SIMILAR_LIMIT = 6;

    private final SpicyItemRepository itemRepository;
    private final SpicyScoreCalculator calculator;

    public SpicyItemService(SpicyItemRepository itemRepository, SpicyScoreCalculator calculator) {
        this.itemRepository = itemRepository;
        this.calculator = calculator;
    }

    /**
     * 키워드로 음식을 검색한다. baseItemId 가 주어지면 각 결과에 상대 지수를 채운다.
     */
    public List<SpicyItemSummary> search(String keyword, Long baseItemId) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>();
        }
        SpicyItem base = baseItemId != null ? findRaw(baseItemId) : null;
        return itemRepository.searchByKeyword(keyword.trim()).stream()
                .map(item -> toSummary(item, base))
                .collect(Collectors.toList());
    }

    /**
     * 음식 상세 정보. baseItemId 가 주어지면 상대 맵기를, 항상 비슷한 매움 목록을 함께 반환한다.
     */
    public SpicyItemDetail getDetail(Long id, Long baseItemId) {
        SpicyItem item = findRaw(id);
        SpicyItem base = baseItemId != null ? findRaw(baseItemId) : null;

        RelativeScore relativeScore = base != null ? calculator.relativeScore(item, base) : null;
        // 비슷한 음식은 이 음식을 기준으로 찾되, 상대 지수 표기는 선택된 기준 음식(없으면 이 음식) 기준
        SpicyItem displayBase = base != null ? base : item;
        List<SimilarItem> similar = findSimilarItems(item, DEFAULT_SIMILAR_RANGE, displayBase, SIMILAR_LIMIT);

        return new SpicyItemDetail(item, calculator.perceivedLabel(item),
                relativeScore, shinRelative(item), similar);
    }

    /**
     * 특정 음식과 비슷한 매운맛의 음식 목록. 상대 지수 표기는 해당 음식(=100) 기준.
     */
    public List<SimilarItem> getSimilar(Long id, int range) {
        SpicyItem item = findRaw(id);
        return findSimilarItems(item, range, item, Integer.MAX_VALUE);
    }

    /**
     * 매운맛 랭킹. category 가 null 이면 전체. 대표 스코빌 내림차순.
     * baseItemId 가 주어지면 상대 지수를 함께 채운다.
     */
    public List<SpicyItemSummary> ranking(Category category, Long baseItemId) {
        SpicyItem base = baseItemId != null ? findRaw(baseItemId) : null;
        List<SpicyItem> items = category != null
                ? itemRepository.findByCategory(category)
                : itemRepository.findAll();

        return items.stream()
                // 공식/추정 수치가 없는 비정형(체감 기반) 항목은 랭킹에서 제외한다.
                // (검색·상세에서는 여전히 '체감 기반'으로 노출)
                .filter(i -> i.representativeShu() != null)
                .sorted(Comparator.comparing(SpicyItem::representativeShu).reversed())
                .map(item -> toSummary(item, base))
                .collect(Collectors.toList());
    }

    /**
     * 이 음식을 기준으로 매운맛이 바로 위(더 매운)/아래(덜 매운)인 음식들을 순위로 반환한다.
     * perSide 만큼 위/아래를 잘라 anchor 를 가운데 두고 정렬(매운 순 내림차순)한다.
     * 수치가 없는 비정형 항목이 기준이면 자기 자신만 반환한다.
     */
    public List<NeighborItem> getNeighbors(Long id, int perSide) {
        SpicyItem anchor = findRaw(id);
        List<NeighborItem> result = new ArrayList<>();

        if (anchor.representativeShu() == null) {
            result.add(new NeighborItem(anchor, null, true));
            return result;
        }

        // 수치 있는 항목만 매운 순으로 정렬
        List<SpicyItem> ranked = itemRepository.findAll().stream()
                .filter(i -> i.representativeShu() != null)
                .sorted(Comparator.comparing(SpicyItem::representativeShu).reversed())
                .collect(Collectors.toList());

        int idx = -1;
        for (int i = 0; i < ranked.size(); i++) {
            if (ranked.get(i).getId().equals(anchor.getId())) {
                idx = i;
                break;
            }
        }
        if (idx < 0) {
            result.add(new NeighborItem(anchor, 100, true));
            return result;
        }

        int start = Math.max(0, idx - perSide);
        int end = Math.min(ranked.size(), idx + perSide + 1);
        for (int i = start; i < end; i++) {
            SpicyItem it = ranked.get(i);
            boolean isAnchor = i == idx;
            Integer rel = isAnchor ? 100 : calculator.relativeScoreValue(it, anchor);
            result.add(new NeighborItem(it, rel, isAnchor));
        }
        return result;
    }

    /**
     * 두 음식의 매운맛을 대결시킨다. 대표 스코빌 기준으로 몇 배 매운지 계산한다.
     */
    public CompareResult compare(Long aId, Long bId) {
        SpicyItem a = findRaw(aId);
        SpicyItem b = findRaw(bId);
        SpicyItemSummary sa = toSummary(a, null);
        SpicyItemSummary sb = toSummary(b, null);

        Integer aShu = a.representativeShu();
        Integer bShu = b.representativeShu();
        if (aShu == null || bShu == null || aShu <= 0 || bShu <= 0) {
            return new CompareResult(sa, sb, null, null, null,
                    "두 음식 중 공식/추정 수치가 없어 정확한 배수 비교는 어렵습니다.");
        }
        if (aShu.equals(bShu)) {
            return new CompareResult(sa, sb, null, null, 1.0,
                    String.format("%s와(과) %s는 매운맛이 비슷합니다.", a.getName(), b.getName()));
        }

        SpicyItem spicier = aShu > bShu ? a : b;
        SpicyItem milder = aShu > bShu ? b : a;
        int hi = Math.max(aShu, bShu);
        int lo = Math.min(aShu, bShu);
        double multiple = Math.round((hi / (double) lo) * 10) / 10.0;
        String text = String.format("%s이(가) %s보다 약 %s배 맵습니다.",
                spicier.getName(), milder.getName(), trimMultiple(multiple));
        return new CompareResult(sa, sb, spicier.getName(), milder.getName(), multiple, text);
    }

    /** 1.0 → "1", 1.3 → "1.3" 처럼 표시 */
    private String trimMultiple(double m) {
        if (m == Math.floor(m)) {
            return String.valueOf((int) m);
        }
        return String.valueOf(m);
    }

    /** canonical 신라면 대비 상대 지수(=100 기준). 없으면 null. */
    public Integer shinRelative(SpicyItem item) {
        return itemRepository.findFirstByName("신라면")
                .map(shin -> calculator.relativeScoreValue(item, shin))
                .orElse(null);
    }

    /** 기준 음식으로 쓰기 좋은 대표 음식 목록 (라면 중심). */
    public List<SpicyItemSummary> baseFoods() {
        return itemRepository.findByCategory(Category.RAMEN).stream()
                .sorted(Comparator.comparing(
                        (SpicyItem i) -> i.representativeShu() == null ? Integer.MIN_VALUE : i.representativeShu()))
                .map(item -> toSummary(item, null))
                .collect(Collectors.toList());
    }

    // ===== 내부 헬퍼 =====

    /** 원본 엔티티 조회 (없으면 404) */
    public SpicyItem findRaw(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("음식을 찾을 수 없습니다. id=" + id));
    }

    private SpicyItemSummary toSummary(SpicyItem item, SpicyItem base) {
        Integer rel = base != null ? calculator.relativeScoreValue(item, base) : null;
        return new SpicyItemSummary(item, calculator.perceivedLabel(item), rel);
    }

    /**
     * anchor 음식과 매운맛이 비슷한(상대 지수 100±range) 음식들을 찾는다.
     * relativeScore 표기는 displayBase(=100) 기준으로 계산한다.
     */
    private List<SimilarItem> findSimilarItems(SpicyItem anchor, int range, SpicyItem displayBase, int limit) {
        Integer anchorShu = anchor.representativeShu();
        if (anchorShu == null || anchorShu <= 0) {
            return new ArrayList<>();
        }

        List<SpicyItem> all = itemRepository.findAll();
        List<SimilarItem> result = new ArrayList<>();

        for (SpicyItem candidate : all) {
            if (candidate.getId().equals(anchor.getId())) {
                continue; // 자기 자신 제외
            }
            Integer candShu = candidate.representativeShu();
            if (candShu == null) {
                continue;
            }
            // anchor 를 100 으로 봤을 때 candidate 의 상대 지수
            int scoreVsAnchor = Math.round(candShu * 100f / anchorShu);
            if (Math.abs(scoreVsAnchor - 100) > range) {
                continue;
            }
            Integer displayScore = calculator.relativeScoreValue(candidate, displayBase);
            result.add(new SimilarItem(
                    candidate.getId(),
                    candidate.getName(),
                    candidate.getCategory() != null ? candidate.getCategory().name() : null,
                    candidate.getCategory() != null ? candidate.getCategory().getLabel() : null,
                    displayScore));
        }

        // anchor 와 가까운 순으로 정렬 (100 에 가까울수록 먼저)
        result.sort(Comparator.comparingInt(si -> {
            int base = si.relativeScore != null ? si.relativeScore : Integer.MAX_VALUE;
            return Math.abs(base - referenceScore(anchor, displayBase));
        }));

        if (result.size() > limit) {
            return result.subList(0, limit);
        }
        return result;
    }

    /** displayBase 기준으로 본 anchor 자신의 상대 지수 (정렬 기준점) */
    private int referenceScore(SpicyItem anchor, SpicyItem displayBase) {
        Integer v = calculator.relativeScoreValue(anchor, displayBase);
        return v != null ? v : 100;
    }
}
