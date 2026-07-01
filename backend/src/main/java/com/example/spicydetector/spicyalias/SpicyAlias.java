package com.example.spicydetector.spicyalias;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 음식의 별칭(검색 보조어). 예: "불닭" -> 불닭볶음면.
 */
@Entity
@Table(name = "spicy_alias")
public class SpicyAlias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 대상 음식 id (단순 참조) */
    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @Column(nullable = false, length = 200)
    private String alias;

    public Long getId() {
        return id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
