package com.example.spicydetector.spicytest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spicydetector.spicytest.dto.QuestionView;
import com.example.spicydetector.spicytest.dto.TestResultView;
import com.example.spicydetector.spicytest.dto.TestSubmitRequest;

/**
 * 맵력테스트 API.
 */
@RestController
@RequestMapping("/api/spicy-test")
public class SpicyTestController {

    private final SpicyTestService testService;

    public SpicyTestController(SpicyTestService testService) {
        this.testService = testService;
    }

    /** 문항 목록 */
    @GetMapping("/questions")
    public List<QuestionView> questions() {
        return testService.getQuestions();
    }

    /** 답변 제출 → 결과 산정 */
    @PostMapping
    public TestResultView submit(@Valid @RequestBody TestSubmitRequest request) {
        return testService.submit(request);
    }

    /** 공유 결과 조회 */
    @GetMapping("/share/{shareToken}")
    public TestResultView share(@PathVariable String shareToken) {
        return testService.getByShareToken(shareToken);
    }
}
