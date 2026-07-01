package com.example.spicydetector.spicytest.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

/**
 * 맵력테스트 제출 요청.
 */
public class TestSubmitRequest {

    @NotEmpty(message = "답변이 필요합니다.")
    private List<Answer> answers;

    /** 기준 음식 id (선택). 지정하면 결과에 반영. */
    private Long baseItemId;

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Long getBaseItemId() {
        return baseItemId;
    }

    public void setBaseItemId(Long baseItemId) {
        this.baseItemId = baseItemId;
    }

    /** 개별 답변 */
    public static class Answer {
        private String questionCode;
        private String answerCode;

        public String getQuestionCode() {
            return questionCode;
        }

        public void setQuestionCode(String questionCode) {
            this.questionCode = questionCode;
        }

        public String getAnswerCode() {
            return answerCode;
        }

        public void setAnswerCode(String answerCode) {
            this.answerCode = answerCode;
        }
    }
}
