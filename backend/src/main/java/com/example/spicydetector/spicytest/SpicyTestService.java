package com.example.spicydetector.spicytest;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.spicydetector.common.NotFoundException;
import com.example.spicydetector.spicytest.dto.QuestionView;
import com.example.spicydetector.spicytest.dto.TestResultView;
import com.example.spicydetector.spicytest.dto.TestSubmitRequest;

/**
 * 맵력테스트 문항 제공, 채점, 결과 저장/공유 조회를 담당한다.
 */
@Service
@Transactional(readOnly = true)
public class SpicyTestService {

    private final SpicyTestResultRepository resultRepository;

    public SpicyTestService(SpicyTestResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    /** 프론트에 제공할 문항 목록 (점수 미포함) */
    public List<QuestionView> getQuestions() {
        return SpicyTestDefinition.QUESTIONS.stream()
                .map(QuestionView::new)
                .collect(Collectors.toList());
    }

    /**
     * 답변을 채점하여 등급을 산정하고 결과를 저장한다.
     */
    @Transactional
    public TestResultView submit(TestSubmitRequest request) {
        int total = 0;
        for (TestSubmitRequest.Answer ans : request.getAnswers()) {
            SpicyTestDefinition.Question q = SpicyTestDefinition.findQuestion(ans.getQuestionCode());
            if (q == null) {
                continue; // 알 수 없는 문항은 무시
            }
            Integer score = q.scoreOf(ans.getAnswerCode());
            if (score != null) {
                total += score;
            }
        }

        SpicyGrade grade = SpicyTestDefinition.resolveGrade(total);
        String summary = buildSummary(grade);
        String shareText = buildShareText(grade);
        String shareToken = generateShareToken();

        // 결과 저장 (공유 조회용)
        SpicyTestResult entity = new SpicyTestResult();
        entity.setResultCode(grade.code);
        entity.setLevel(grade.level);
        entity.setBadgeName(grade.badgeName);
        entity.setBaseItemId(request.getBaseItemId());
        entity.setToleranceMin(grade.toleranceMin);
        entity.setToleranceMax(grade.toleranceMax);
        entity.setResultSummary(summary);
        entity.setShareToken(shareToken);
        resultRepository.save(entity);

        return new TestResultView(grade, total, summary, shareToken, shareText);
    }

    /** 공유 토큰으로 저장된 결과 조회 */
    public TestResultView getByShareToken(String shareToken) {
        SpicyTestResult saved = resultRepository.findByShareToken(shareToken)
                .orElseThrow(() -> new NotFoundException("공유된 결과를 찾을 수 없습니다."));
        SpicyGrade grade = findGradeByCode(saved.getResultCode());
        return TestResultView.from(saved, grade, buildShareText(grade));
    }

    // ===== 내부 헬퍼 =====

    private SpicyGrade findGradeByCode(String code) {
        return SpicyTestDefinition.GRADES.stream()
                .filter(g -> g.code.equals(code))
                .findFirst()
                .orElse(SpicyTestDefinition.GRADES.get(0));
    }

    private String buildSummary(SpicyGrade grade) {
        return String.format(
                "당신의 맵력은 \"%s\"입니다. 안전 구간은 %s, 도전 구간은 %s, 위험 구간은 %s 입니다.",
                grade.badgeName, grade.safeZone, grade.challengeZone, grade.dangerZone);
    }

    private String buildShareText(SpicyGrade grade) {
        return String.format("나의 맵력은? %s (Lv.%d) — 매움판독기에서 확인하기", grade.badgeName, grade.level);
    }

    /** 짧고 URL 안전한 공유 토큰 생성 */
    private String generateShareToken() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    }
}
