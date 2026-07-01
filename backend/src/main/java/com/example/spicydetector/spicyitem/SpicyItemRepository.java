package com.example.spicydetector.spicyitem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.spicydetector.common.Category;

public interface SpicyItemRepository extends JpaRepository<SpicyItem, Long> {

    /**
     * 이름 또는 별칭(spicy_alias)에 키워드가 포함된 음식을 검색한다.
     * 대소문자 구분 없이 부분 일치. 대표 스코빌(있으면 shu_value) 내림차순으로 정렬.
     */
    @Query("SELECT DISTINCT i FROM SpicyItem i "
            + "WHERE LOWER(i.name) LIKE LOWER(CONCAT('%', :keyword, '%')) "
            + "OR i.id IN (SELECT a.itemId FROM SpicyAlias a "
            + "            WHERE LOWER(a.alias) LIKE LOWER(CONCAT('%', :keyword, '%'))) "
            + "ORDER BY i.shuValue DESC NULLS LAST")
    List<SpicyItem> searchByKeyword(@Param("keyword") String keyword);

    /** 카테고리별 조회 (정렬은 서비스에서 대표 스코빌 기준으로 처리) */
    List<SpicyItem> findByCategory(Category category);
}
