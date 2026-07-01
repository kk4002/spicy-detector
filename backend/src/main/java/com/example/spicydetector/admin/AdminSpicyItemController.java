package com.example.spicydetector.admin;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spicydetector.common.Category;
import com.example.spicydetector.common.ConfidenceLevel;
import com.example.spicydetector.common.SourceType;
import com.example.spicydetector.spicyitem.SpicyItem;

/**
 * 관리자용 음식 데이터 등록/수정 API.
 *
 * <p>MVP 단계에서는 인증을 두지 않는다. 운영 배포 전 반드시 인증/인가를 추가해야 한다.</p>
 */
@RestController
@RequestMapping("/api/admin/spicy-items")
public class AdminSpicyItemController {

    private final AdminSpicyItemService adminService;

    public AdminSpicyItemController(AdminSpicyItemService adminService) {
        this.adminService = adminService;
    }

    /** 음식 등록 */
    @PostMapping
    public SpicyItem create(@Valid @RequestBody AdminSpicyItemRequest req) {
        return adminService.create(req);
    }

    /** 음식 수정 */
    @PutMapping("/{id}")
    public SpicyItem update(@PathVariable Long id, @Valid @RequestBody AdminSpicyItemRequest req) {
        return adminService.update(id, req);
    }

    /** 폼 구성을 위한 선택 항목 메타데이터 (카테고리/출처/신뢰도) */
    @GetMapping("/meta")
    public Object meta() {
        java.util.Map<String, Object> body = new java.util.LinkedHashMap<>();
        java.util.List<java.util.Map<String, String>> categories = new java.util.ArrayList<>();
        for (Category c : Category.values()) {
            java.util.Map<String, String> m = new java.util.LinkedHashMap<>();
            m.put("code", c.name());
            m.put("label", c.getLabel());
            categories.add(m);
        }
        body.put("categories", categories);
        body.put("sourceTypes", SourceType.values());
        body.put("confidenceLevels", ConfidenceLevel.values());
        return body;
    }
}
