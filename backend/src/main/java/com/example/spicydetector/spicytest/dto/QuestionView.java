package com.example.spicydetector.spicytest.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.example.spicydetector.spicytest.SpicyTestDefinition;

/**
 * 프론트에 노출하는 문항 정보 (점수는 포함하지 않음).
 */
public class QuestionView {

    public final String code;
    public final String text;
    public final List<OptionView> options;

    public QuestionView(SpicyTestDefinition.Question q) {
        this.code = q.code;
        this.text = q.text;
        this.options = q.options.stream()
                .map(o -> new OptionView(o.code, o.label))
                .collect(Collectors.toList());
    }

    /** 선택지 (점수 제외) */
    public static class OptionView {
        public final String code;
        public final String label;

        public OptionView(String code, String label) {
            this.code = code;
            this.label = label;
        }
    }
}
