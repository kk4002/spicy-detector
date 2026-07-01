package com.example.spicydetector.spicyalias;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpicyAliasRepository extends JpaRepository<SpicyAlias, Long> {

    List<SpicyAlias> findByItemId(Long itemId);
}
