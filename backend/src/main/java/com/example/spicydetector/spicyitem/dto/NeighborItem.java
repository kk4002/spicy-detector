package com.example.spicydetector.spicyitem.dto;

import com.example.spicydetector.spicyitem.SpicyItem;

/**
 * "이 음식 기준 위/아래 순위"에 표시되는 한 항목.
 * relativeToAnchor 는 기준 음식(anchor)=100 대비 상대 지수.
 */
public class NeighborItem {

    public final Long id;
    public final String name;
    public final String category;
    public final String categoryLabel;
    public final Integer shuValue;
    public final String shuText;
    public final Integer relativeToAnchor;
    public final boolean anchor;

    public NeighborItem(SpicyItem item, Integer relativeToAnchor, boolean anchor) {
        this.id = item.getId();
        this.name = item.getName();
        this.category = item.getCategory() != null ? item.getCategory().name() : null;
        this.categoryLabel = item.getCategory() != null ? item.getCategory().getLabel() : null;
        this.shuValue = item.getShuValue();
        this.shuText = item.getShuText();
        this.relativeToAnchor = relativeToAnchor;
        this.anchor = anchor;
    }
}
