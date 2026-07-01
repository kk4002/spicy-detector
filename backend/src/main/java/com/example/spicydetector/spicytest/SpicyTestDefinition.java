package com.example.spicydetector.spicytest;

import java.util.Arrays;
import java.util.List;

/**
 * 맵력테스트의 문항/선택지/점수와 등급 구간을 정의한 정적 설정.
 *
 * <p>점수 산정은 전적으로 서버에서 이루어지며, 프론트에는 점수를 노출하지 않는다.
 * 총점 범위는 0 ~ 220 이며, 아래 GRADES 구간으로 매핑된다.</p>
 */
public final class SpicyTestDefinition {

    private SpicyTestDefinition() {
    }

    /** 문항 선택지 (내부 설정용, 점수 포함) */
    public static final class Option {
        public final String code;
        public final String label;
        public final int score;

        public Option(String code, String label, int score) {
            this.code = code;
            this.label = label;
            this.score = score;
        }
    }

    /** 문항 (내부 설정용) */
    public static final class Question {
        public final String code;
        public final String text;
        public final List<Option> options;

        public Question(String code, String text, Option... options) {
            this.code = code;
            this.text = text;
            this.options = Arrays.asList(options);
        }

        public Integer scoreOf(String answerCode) {
            for (Option o : options) {
                if (o.code.equals(answerCode)) {
                    return o.score;
                }
            }
            return null;
        }
    }

    /** 문항 목록 (5문항, 총점 0~180) */
    public static final List<Question> QUESTIONS = Arrays.asList(
            new Question("SHIN_RAMEN", "신라면은 어느 정도인가요?",
                    new Option("HARD", "맵고 힘들다", 0),
                    new Option("HOT", "꽤 맵지만 먹는다", 6),
                    new Option("OK", "적당히 매운 정도", 12),
                    new Option("EASY", "편하게 먹는다", 20)),

            new Question("YEOL_RAMEN", "열라면(또는 신라면 더레드)은 가능한가요?",
                    new Option("NO", "못 먹거나 안 먹는다", 0),
                    new Option("HARD", "힘들지만 먹는다", 8),
                    new Option("OK", "무난하게 가능", 18),
                    new Option("EASY", "아주 편하게 가능", 30)),

            new Question("BULDAK", "불닭볶음면은 가능한가요?",
                    new Option("NO", "못 먹는다", 0),
                    new Option("HARD", "거의 불가능", 12),
                    new Option("HALF", "반 정도는 가능", 28),
                    new Option("EASY", "문제없이 가능", 50)),

            new Question("TTEOKBOKKI", "매운 떡볶이는 어느 단계까지 먹나요?",
                    new Option("MILD", "순한맛", 0),
                    new Option("NORMAL", "보통맛", 14),
                    new Option("HOT", "매운맛", 28),
                    new Option("EXTREME", "아주 매운맛(또는 그 이상)", 40)),

            new Question("REGRET", "매운 음식 먹고 다음날 후회한 적 있나요?",
                    new Option("ALWAYS", "자주 후회한다", 0),
                    new Option("SOMETIMES", "가끔 그렇다", 12),
                    new Option("RARELY", "거의 없다", 26),
                    new Option("NEVER", "전혀, 오히려 더 찾는다", 40))
    );

    /** 등급 구간 (총점 0~180 기준). tolerance* 는 신라면=100 환산 예상 맵력. */
    public static final List<SpicyGrade> GRADES = Arrays.asList(
            new SpicyGrade(1, "LV1", "순한맛 시민", 0, 22, 40, 90,
                    "순한 음식", "신라면", "열라면 이상",
                    Arrays.asList("진라면 순한맛", "국물 떡볶이 순한맛"),
                    Arrays.asList("불닭볶음면", "핵불닭볶음면")),
            new SpicyGrade(2, "LV2", "신라면 생존자", 23, 45, 90, 110,
                    "신라면", "열라면", "불닭볶음면 이상",
                    Arrays.asList("신라면", "진라면 매운맛"),
                    Arrays.asList("핵불닭볶음면", "디진다돈가스급")),
            new SpicyGrade(3, "LV3", "열라면 통과자", 46, 72, 110, 140,
                    "신라면 ~ 열라면", "불닭볶음면", "핵불닭 이상",
                    Arrays.asList("열라면", "신라면 더레드"),
                    Arrays.asList("핵불닭볶음면", "디진다돈가스급")),
            new SpicyGrade(4, "LV4", "불닭 도전자", 73, 100, 140, 180,
                    "신라면 ~ 열라면", "불닭볶음면", "핵불닭 이상",
                    Arrays.asList("불닭볶음면", "엽기떡볶이 매운맛"),
                    Arrays.asList("핵불닭볶음면", "틈새라면")),
            new SpicyGrade(5, "LV5", "불닭 생존자", 101, 128, 180, 230,
                    "신라면 ~ 불닭볶음면", "핵불닭볶음면", "디진다돈가스급 이상",
                    Arrays.asList("불닭볶음면", "틈새라면"),
                    Arrays.asList("핵불닭볶음면")),
            new SpicyGrade(6, "LV6", "핵불닭 생환자", 129, 150, 230, 300,
                    "불닭볶음면까지 무난", "핵불닭볶음면", "캡사이신 원액급",
                    Arrays.asList("핵불닭볶음면", "틈새라면"),
                    Arrays.asList("순수 캡사이신")),
            new SpicyGrade(7, "LV7", "캡사이신 전사", 151, 170, 300, 400,
                    "핵불닭볶음면까지 즐김", "고스트페퍼급", "몸 상하는 구간",
                    Arrays.asList("핵불닭볶음면", "하바네로"),
                    Arrays.asList("무리한 캡사이신 챌린지")),
            new SpicyGrade(8, "LV8", "위장 파괴자", 171, 180, 400, 600,
                    "웬만한 매운맛은 다 무난", "고스트페퍼~캐롤라이나 리퍼급", "안전을 위해 자제 권장",
                    Arrays.asList("하바네로", "고스트페퍼"),
                    Arrays.asList("캐롤라이나 리퍼", "순수 캡사이신"))
    );

    /** 총점을 등급으로 변환. 범위를 벗어나면 경계 등급으로 보정. */
    public static SpicyGrade resolveGrade(int totalScore) {
        for (SpicyGrade g : GRADES) {
            if (g.contains(totalScore)) {
                return g;
            }
        }
        // 안전 보정: 음수는 최저, 초과는 최고 등급
        return totalScore < GRADES.get(0).minScore
                ? GRADES.get(0)
                : GRADES.get(GRADES.size() - 1);
    }

    /** 이론상 최대 총점 (진행률 계산 등에 사용) */
    public static int maxTotalScore() {
        int sum = 0;
        for (Question q : QUESTIONS) {
            int best = 0;
            for (Option o : q.options) {
                best = Math.max(best, o.score);
            }
            sum += best;
        }
        return sum;
    }

    /** 문항 코드로 문항 조회 */
    public static Question findQuestion(String code) {
        for (Question q : QUESTIONS) {
            if (q.code.equals(code)) {
                return q;
            }
        }
        return null;
    }
}
