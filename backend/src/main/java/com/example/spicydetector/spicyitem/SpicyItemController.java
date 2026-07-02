package com.example.spicydetector.spicyitem;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.spicydetector.common.Category;
import com.example.spicydetector.spicyitem.dto.SimilarItem;
import com.example.spicydetector.spicyitem.dto.SpicyItemDetail;
import com.example.spicydetector.spicyitem.dto.SpicyItemSummary;

/**
 * 음식 검색/상세/비슷한 매움/랭킹 API.
 */
@RestController
@RequestMapping("/api/spicy-items")
public class SpicyItemController {

    private final SpicyItemService itemService;

    public SpicyItemController(SpicyItemService itemService) {
        this.itemService = itemService;
    }

    /** 음식 검색 */
    @GetMapping("/search")
    public Map<String, Object> search(@RequestParam String keyword,
                                      @RequestParam(required = false) Long baseItemId) {
        List<SpicyItemSummary> items = itemService.search(keyword, baseItemId);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("keyword", keyword);
        body.put("items", items);
        return body;
    }

    /** 기준 음식으로 쓰기 좋은 대표 음식 목록 */
    @GetMapping("/base-foods")
    public List<SpicyItemSummary> baseFoods() {
        return itemService.baseFoods();
    }

    /** 매운맛 랭킹 */
    @GetMapping("/ranking")
    public Map<String, Object> ranking(@RequestParam(required = false) String category,
                                       @RequestParam(required = false) Long baseItemId) {
        Category cat = parseCategory(category);
        List<SpicyItemSummary> items = itemService.ranking(cat, baseItemId);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("category", cat != null ? cat.name() : null);
        body.put("items", items);
        return body;
    }

    /** 음식 vs 음식 대결 */
    @GetMapping("/compare")
    public com.example.spicydetector.spicyitem.dto.CompareResult compare(@RequestParam Long aId,
                                                                         @RequestParam Long bId) {
        return itemService.compare(aId, bId);
    }

    /** 음식 상세 */
    @GetMapping("/{id}")
    public SpicyItemDetail detail(@PathVariable Long id,
                                  @RequestParam(required = false) Long baseItemId) {
        return itemService.getDetail(id, baseItemId);
    }

    /** 이 음식 기준 위/아래 매운맛 순위 (로컬 랭킹) */
    @GetMapping("/{id}/neighbors")
    public Map<String, Object> neighbors(@PathVariable Long id,
                                         @RequestParam(defaultValue = "5") int perSide) {
        SpicyItem item = itemService.findRaw(id);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("anchorId", id);
        body.put("anchorName", item.getName());
        body.put("items", itemService.getNeighbors(id, perSide));
        return body;
    }

    /** 비슷한 매움 찾기 */
    @GetMapping("/{id}/similar")
    public Map<String, Object> similar(@PathVariable Long id,
                                       @RequestParam(defaultValue = "20") int range) {
        SpicyItem item = itemService.findRaw(id);
        List<SimilarItem> items = itemService.getSimilar(id, range);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("baseItem", item.getName());
        body.put("range", range);
        body.put("items", items);
        return body;
    }

    private Category parseCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            return null;
        }
        try {
            return Category.valueOf(category.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("알 수 없는 카테고리입니다: " + category);
        }
    }
}
