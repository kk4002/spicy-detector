package com.example.spicydetector.spicytest;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpicyTestResultRepository extends JpaRepository<SpicyTestResult, Long> {

    Optional<SpicyTestResult> findByShareToken(String shareToken);
}
