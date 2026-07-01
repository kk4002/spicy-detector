package com.example.spicydetector.admin;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.spicydetector.common.ConfidenceLevel;
import com.example.spicydetector.common.NotFoundException;
import com.example.spicydetector.spicyalias.SpicyAlias;
import com.example.spicydetector.spicyalias.SpicyAliasRepository;
import com.example.spicydetector.spicyitem.SpicyItem;
import com.example.spicydetector.spicyitem.SpicyItemRepository;

/**
 * 관리자용 음식 데이터 등록/수정 서비스.
 */
@Service
@Transactional
public class AdminSpicyItemService {

    private final SpicyItemRepository itemRepository;
    private final SpicyAliasRepository aliasRepository;

    public AdminSpicyItemService(SpicyItemRepository itemRepository, SpicyAliasRepository aliasRepository) {
        this.itemRepository = itemRepository;
        this.aliasRepository = aliasRepository;
    }

    public SpicyItem create(AdminSpicyItemRequest req) {
        SpicyItem item = new SpicyItem();
        apply(item, req);
        SpicyItem saved = itemRepository.save(item);
        saveAliases(saved.getId(), req.getAliases(), true);
        return saved;
    }

    public SpicyItem update(Long id, AdminSpicyItemRequest req) {
        SpicyItem item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("음식을 찾을 수 없습니다. id=" + id));
        apply(item, req);
        SpicyItem saved = itemRepository.save(item);
        saveAliases(saved.getId(), req.getAliases(), false);
        return saved;
    }

    /** 요청 값을 엔티티에 반영. 신뢰도 기본값은 UNKNOWN. */
    private void apply(SpicyItem item, AdminSpicyItemRequest req) {
        item.setName(req.getName());
        item.setBrand(req.getBrand());
        item.setCategory(req.getCategory());
        item.setShuMin(req.getShuMin());
        item.setShuMax(req.getShuMax());
        item.setShuValue(req.getShuValue());
        item.setShuText(req.getShuText());
        item.setOfficialYn(req.getOfficialYn() != null ? req.getOfficialYn() : Boolean.FALSE);
        item.setSourceType(req.getSourceType());
        item.setSourceUrl(req.getSourceUrl());
        item.setSourceNote(req.getSourceNote());
        item.setConfidenceLevel(req.getConfidenceLevel() != null ? req.getConfidenceLevel() : ConfidenceLevel.UNKNOWN);
        item.setDescription(req.getDescription());
    }

    /**
     * 별칭 저장. 수정 시(replaceExisting=false)에도 기존 별칭을 지우고 새로 등록한다.
     */
    private void saveAliases(Long itemId, List<String> aliases, boolean isCreate) {
        if (!isCreate) {
            List<SpicyAlias> existing = aliasRepository.findByItemId(itemId);
            aliasRepository.deleteAll(existing);
        }
        if (aliases == null) {
            return;
        }
        for (String alias : aliases) {
            if (alias == null || alias.trim().isEmpty()) {
                continue;
            }
            SpicyAlias a = new SpicyAlias();
            a.setItemId(itemId);
            a.setAlias(alias.trim());
            aliasRepository.save(a);
        }
    }
}
