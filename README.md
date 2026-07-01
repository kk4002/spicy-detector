# 매움판독기 (Spicy Detector)

기준 음식으로 매운맛을 비교하고, 내가 버틸 수 있을지 판독하는 **스코빌 기반 매운맛 체감 비교 서비스**입니다.

> 신라면은 버티는데, 불닭은 가능할까?
> 익숙한 음식 기준으로 매운맛을 판독해보세요.

---

## 핵심 컨셉

단순 스코빌 백과가 아니라 **체감 비교 서비스**입니다. 세 가지를 분리해서 보여줍니다.

1. **스코빌 지수** (참고값)
2. **기준 음식 대비 상대 맵기** (예: 신라면 = 100)
3. **사용자 체감 맵기**

모든 수치에는 **신뢰도 등급**(HIGH / MEDIUM / LOW / UNKNOWN)과 **출처**를 함께 표시합니다.
공식 수치가 불확실한 음식은 공식값처럼 단정하지 않고 체감 기반으로 분류합니다.

---

## 기술 스택

| 구분 | 스택 | 비고 |
|------|------|------|
| 백엔드 | Spring Boot 2.7.x, Java 11, Spring Data JPA | Boot 3은 Java 17 필요 → 로컬 Java 11 기준 2.7 채택 |
| DB | H2 (인메모리, 기본) / PostgreSQL 이식 가능 | 스키마는 PostgreSQL 기준으로 설계 |
| 프론트 | React 18 + Vite | |

---

## 실행 방법

### 1. 백엔드 (기본 포트 8080)

```bash
cd backend
./mvnw spring-boot:run        # Windows: mvnw.cmd spring-boot:run
```

- H2 콘솔: http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:spicydb`)
- 앱 기동 시 `data.sql`의 샘플 음식 데이터가 자동 적재됩니다.

### 2. 프론트엔드 (기본 포트 5173)

```bash
cd frontend
npm install
npm run dev
```

- 개발 서버는 `/api` 요청을 백엔드(8080)로 프록시합니다.

---

## 주요 API

| 기능 | 메서드 | 경로 |
|------|--------|------|
| 음식 검색 | GET | `/api/spicy-items/search?keyword=불닭` |
| 음식 상세 | GET | `/api/spicy-items/{id}?baseItemId=1` |
| 비슷한 매움 | GET | `/api/spicy-items/{id}/similar?range=20` |
| 매운맛 랭킹 | GET | `/api/spicy-items/ranking?category=RAMEN` |
| 기준 음식 목록 | GET | `/api/spicy-items/base-foods` |
| 맵력테스트 문항 | GET | `/api/spicy-test/questions` |
| 맵력테스트 제출 | POST | `/api/spicy-test` |
| 공유 결과 조회 | GET | `/api/spicy-test/share/{shareToken}` |
| 관리자 음식 등록 | POST | `/api/admin/spicy-items` |
| 관리자 음식 수정 | PUT | `/api/admin/spicy-items/{id}` |

---

## 패키지 구조 (백엔드)

```
com.example.spicydetector
 ├─ spicyitem   음식 데이터/검색/상세/상대비교/비슷한매움/랭킹
 ├─ spicytest   맵력테스트 문항/채점/결과/공유
 ├─ spicyreview 사용자 체감 리뷰 (엔티티 준비)
 ├─ source      출처 (엔티티 준비)
 ├─ admin       관리자 데이터 등록/수정
 ├─ ai          AI 보조 기능 자리(추후)
 └─ common      공통(CORS, 예외, 응답)
```

---

## 데이터 신뢰도 정책

| 등급 | 의미 |
|------|------|
| HIGH | 제조사 공식값 또는 신뢰 가능한 공개 자료 |
| MEDIUM | 여러 출처에서 반복 확인되는 값 |
| LOW | 커뮤니티/사용자 추정값 |
| UNKNOWN | 수치 없음, 체감 기반 |

> 초기 샘플 데이터의 스코빌 수치는 대부분 추정/체감 기반이며 `source_type=SAMPLE`, `confidence_level=LOW/UNKNOWN`으로 저장됩니다.
> 정확한 SHU를 모르는 음식은 임의로 공식값처럼 저장하지 않습니다.

---

## 개발 단계

- **1단계(구현됨)**: 메인/검색/상세/기준 음식 선택/상대 맵기 계산
- **2단계(구현됨)**: 비슷한 매움 추천, 매운맛 랭킹, 관리자 등록/수정
- **3단계(구현됨)**: 맵력테스트, 결과 등급/뱃지, 공유 링크
- **4단계(예정)**: 사용자 체감 리뷰, 체감 평균 반영
- **5단계(예정)**: AI 보조 기능(음식명 매칭, 리뷰 요약, 자연어 질문)

---

## 주의사항

- 스코빌 지수는 절대적인 체감 매운맛이 아닙니다. 형태·양·조리 방식에 따라 체감은 달라질 수 있습니다.
- 공식 출처가 없는 수치는 추정값으로 표시합니다.
- 사용자의 건강 상태나 알레르기에 대한 의학적 판단은 하지 않습니다.
- 콘텐츠는 재미있게 가되, 수치와 출처 표시는 신중하게 합니다.
