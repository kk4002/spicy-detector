-- ============================================================
-- 매움판독기 초기 데이터 (출처/신뢰도 세분화)
--
-- 신뢰도 정책:
--   HIGH    : 제조사 공식/연구기관/공식 보도자료
--   MEDIUM  : 여러 참고자료에서 반복 확인 (고추 품종/핫소스 범위 등)
--   LOW     : 커뮤니티/블로그/사용자 체감 추정
--   UNKNOWN : 공식 수치 없음 (체감 기반)
--
-- 주의:
--   - 라면/떡볶이/음식점 메뉴는 공식 스코빌이 거의 없어 대부분 커뮤니티 추정(LOW).
--   - 농심이 공개한 수치(신라면 3,400 / 더레드 7,500 / 앵그리너구리 6,080 등)만 제조사 공식(HIGH).
--   - 고추 품종/핫소스는 공개 참고자료의 '범위값'을 사용(REFERENCE_SITE, MEDIUM).
--   - 공식값이 없는 비정형 항목(예: 디진다돈가스)은 UNKNOWN 으로 두고 랭킹에서 제외.
-- ============================================================

INSERT INTO spicy_item
    (id, name, brand, category, shu_min, shu_max, shu_value, shu_text,
     official_yn, source_type, source_url, source_note, confidence_level, description, created_at, updated_at)
VALUES
-- ===== 라면 (제조사 공식: HIGH) =====
(1, '신라면', '농심', 'RAMEN', NULL, NULL, 3400, '3,400 SHU',
 TRUE, 'OFFICIAL_MANUFACTURER', NULL, '농심 공개 수치', 'HIGH',
 '한국인에게 가장 익숙한 매운맛 기준선. 상대 비교의 기본 기준 음식으로 적합합니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, '신라면 더레드', '농심', 'RAMEN', NULL, NULL, 7500, '7,500 SHU',
 TRUE, 'OFFICIAL_MANUFACTURER', NULL, '농심 출시 보도자료', 'HIGH',
 '기존 신라면보다 두 배 이상 맵게 나온 버전.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, '신라면 제페토 큰사발', '농심', 'RAMEN', NULL, NULL, 6000, '6,000 SHU',
 TRUE, 'OFFICIAL_MANUFACTURER', NULL, '농심 콘텐츠 공개 수치', 'HIGH',
 '컬래버 제품으로 신라면보다 맵게 설정된 버전.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, '앵그리 너구리', '농심', 'RAMEN', NULL, NULL, 6080, '6,080 SHU',
 TRUE, 'OFFICIAL_MANUFACTURER', NULL, '농심 공개 수치', 'HIGH',
 '너구리의 매운맛 강화 버전.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- ===== 라면 (커뮤니티 추정: LOW) =====
(5, '너구리', '농심', 'RAMEN', NULL, NULL, 1500, '약 1,500 SHU (추정)',
 FALSE, 'COMMUNITY_ESTIMATE', NULL, '커뮤니티 추정', 'LOW',
 '매운맛보다는 얼큰함에 가깝습니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, '진라면 매운맛', '오뚜기', 'RAMEN', NULL, NULL, 2000, '약 2,000 SHU (추정)',
 FALSE, 'COMMUNITY_ESTIMATE', NULL, '커뮤니티 추정', 'LOW',
 '신라면과 비슷하거나 살짝 순한 편으로 자주 언급됩니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, '열라면', '오뚜기', 'RAMEN', NULL, NULL, 5000, '약 5,000 SHU (추정)',
 FALSE, 'COMMUNITY_ESTIMATE', NULL, '후기 기반 추정', 'LOW',
 '신라면보다 확실히 매운 국물 라면.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, '불닭볶음면', '삼양', 'RAMEN', NULL, NULL, 4404, '약 4,404 SHU',
 FALSE, 'NEWS', NULL, '미디어에서 다수 인용(제품·시점별 차이 가능)', 'MEDIUM',
 '국물 없이 소스로 매운맛이 집중되어 체감은 수치 이상으로 느껴질 수 있습니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, '까르보 불닭볶음면', '삼양', 'RAMEN', NULL, NULL, 2600, '약 2,600 SHU (추정)',
 FALSE, 'COMMUNITY_ESTIMATE', NULL, '커뮤니티 추정', 'LOW',
 '크림 소스로 매운맛이 완화된 버전.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, '핵불닭볶음면', '삼양', 'RAMEN', 8000, 13000, 10000, '약 8,000~13,000 SHU (추정)',
 FALSE, 'COMMUNITY_ESTIMATE', NULL, '범위로 추정', 'LOW',
 '불닭볶음면의 강화 버전. 확실한 도전 구간입니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, '틈새라면', '팔도', 'RAMEN', NULL, NULL, 9413, '약 9,413 SHU',
 FALSE, 'NEWS', NULL, '다수 인용되는 수치', 'MEDIUM',
 '국물 라면 중 최상위권 매운맛으로 유명합니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, '삼양라면 오리지널', '삼양', 'RAMEN', NULL, NULL, 1000, '약 1,000 SHU (추정)',
 FALSE, 'COMMUNITY_ESTIMATE', NULL, '커뮤니티 추정', 'LOW',
 '순한 편의 클래식 라면.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, '진짬뽕', '오뚜기', 'RAMEN', NULL, NULL, 2200, '약 2,200 SHU (추정)',
 FALSE, 'COMMUNITY_ESTIMATE', NULL, '커뮤니티 추정', 'LOW',
 '얼큰한 짬뽕맛 라면.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- ===== 떡볶이 (커뮤니티 추정: LOW) =====
(14, '엽기떡볶이 착한맛', '엽기떡볶이', 'TTEOKBOKKI', NULL, NULL, 800, '약 800 SHU (추정)',
 FALSE, 'COMMUNITY_ESTIMATE', NULL, '메뉴/매장별 차이 큼', 'LOW',
 '떡볶이 입문용 순한 단계.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, '엽기떡볶이 매운맛', '엽기떡볶이', 'TTEOKBOKKI', NULL, NULL, 3000, '약 3,000 SHU (추정)',
 FALSE, 'COMMUNITY_ESTIMATE', NULL, '메뉴/매장별 차이 큼', 'LOW',
 '당도와 소스 양에 따라 체감이 크게 달라집니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, '신전떡볶이 매운맛', '신전떡볶이', 'TTEOKBOKKI', NULL, NULL, 2500, '약 2,500 SHU (추정)',
 FALSE, 'COMMUNITY_ESTIMATE', NULL, '커뮤니티 추정', 'LOW',
 '분식 프랜차이즈 매운맛 라인.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, '배떡 로제떡볶이', '배떡', 'TTEOKBOKKI', NULL, NULL, 1200, '약 1,200 SHU (추정)',
 FALSE, 'COMMUNITY_ESTIMATE', NULL, '커뮤니티 추정', 'LOW',
 '로제 소스로 매운맛이 완화된 편.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- ===== 핫소스 (참고 자료 범위: MEDIUM) =====
(18, '프랭크스 레드핫', 'Frank''s', 'HOTSAUCE', 400, 500, 450, '약 450 SHU',
 FALSE, 'REFERENCE_SITE', NULL, '공개 참고자료 범위', 'MEDIUM',
 '순한 편의 미국식 핫소스.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, '촐룰라 핫소스', 'Cholula', 'HOTSAUCE', 1000, 2000, 1500, '약 1,000~2,000 SHU',
 FALSE, 'REFERENCE_SITE', NULL, '공개 참고자료 범위', 'MEDIUM',
 '멕시코식 순한~보통 핫소스.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, '스리라차 소스', 'Huy Fong', 'HOTSAUCE', 1000, 2500, 2000, '약 1,000~2,500 SHU',
 FALSE, 'REFERENCE_SITE', NULL, '공개 참고자료 범위', 'MEDIUM',
 '달콤·마늘향이 있는 핫소스.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(21, '타바스코 오리지널', 'Tabasco', 'HOTSAUCE', 2500, 5000, 3750, '약 2,500~5,000 SHU',
 FALSE, 'REFERENCE_SITE', NULL, '오리지널 레드 기준 범위', 'MEDIUM',
 '신맛이 강해 체감은 수치보다 낮게 느껴지기도 합니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(22, '타바스코 하바네로', 'Tabasco', 'HOTSAUCE', 5000, 9000, 7000, '약 5,000~9,000 SHU',
 FALSE, 'REFERENCE_SITE', NULL, '공개 참고자료 범위', 'MEDIUM',
 '하바네로를 쓴 매운 라인.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(23, '데이브스 인새니티 소스', 'Dave''s', 'HOTSAUCE', 150000, 250000, 180000, '약 18만 SHU',
 FALSE, 'REFERENCE_SITE', NULL, '공개 참고자료 범위', 'MEDIUM',
 '고강도 익스트림 핫소스.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- ===== 고추 품종 (참고 자료 범위: MEDIUM) =====
(24, '파프리카', NULL, 'PEPPER', 0, 0, 0, '0 SHU',
 FALSE, 'REFERENCE_SITE', NULL, '매운맛 없음(기준점)', 'MEDIUM',
 '매운맛이 없는 단고추. 스코빌 0의 기준점.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(25, '꽈리고추', NULL, 'PEPPER', 100, 1000, 500, '약 100~1,000 SHU',
 FALSE, 'REFERENCE_SITE', NULL, '개체차 큼', 'MEDIUM',
 '대체로 순하지만 가끔 매운 개체가 섞입니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(26, '청양고추', NULL, 'PEPPER', 4000, 12000, 8000, '약 4,000~12,000 SHU',
 FALSE, 'REFERENCE_SITE', NULL, '개체차 큼', 'MEDIUM',
 '생고추 중 익숙한 매운맛 기준. 개체 편차가 큽니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27, '할라피뇨', NULL, 'PEPPER', 2500, 8000, 5000, '약 2,500~8,000 SHU',
 FALSE, 'REFERENCE_SITE', NULL, '공개 참고자료 범위', 'MEDIUM',
 '멕시코 요리에 흔히 쓰이는 고추.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(28, '세라노', NULL, 'PEPPER', 10000, 23000, 16000, '약 1만~2.3만 SHU',
 FALSE, 'REFERENCE_SITE', NULL, '공개 참고자료 범위', 'MEDIUM',
 '할라피뇨보다 한 단계 매운 고추.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(29, '카옌페퍼', NULL, 'PEPPER', 30000, 50000, 40000, '약 3만~5만 SHU',
 FALSE, 'REFERENCE_SITE', NULL, '공개 참고자료 범위', 'MEDIUM',
 '건조·분말로 많이 쓰이는 매운 고추.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(30, '타이 버즈아이', NULL, 'PEPPER', 50000, 100000, 75000, '약 5만~10만 SHU',
 FALSE, 'REFERENCE_SITE', NULL, '공개 참고자료 범위', 'MEDIUM',
 '작지만 매우 매운 태국 고추.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31, '하바네로', NULL, 'PEPPER', 100000, 350000, 200000, '약 10만~35만 SHU',
 FALSE, 'REFERENCE_SITE', NULL, '널리 알려진 공개 자료 범위', 'MEDIUM',
 '라면류와는 차원이 다른 구간의 고추.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(32, '스카치보넷', NULL, 'PEPPER', 100000, 350000, 200000, '약 10만~35만 SHU',
 FALSE, 'REFERENCE_SITE', NULL, '공개 참고자료 범위', 'MEDIUM',
 '하바네로와 비슷한 강도의 카리브 고추.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(33, '고스트페퍼(부트 졸로키아)', NULL, 'PEPPER', 800000, 1041427, 1000000, '약 80만~104만 SHU',
 FALSE, 'REFERENCE_SITE', NULL, '공개 참고자료 범위', 'MEDIUM',
 '한때 세계에서 가장 매운 고추로 알려진 품종.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(34, '캐롤라이나 리퍼', NULL, 'PEPPER', 1400000, 2200000, 1800000, '약 140만~220만 SHU',
 FALSE, 'REFERENCE_SITE', NULL, '공개 참고자료 범위', 'MEDIUM',
 '세계 최상위권 매운 고추.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(35, '페퍼 X', NULL, 'PEPPER', NULL, NULL, 2693000, '약 269만 SHU',
 FALSE, 'REFERENCE_SITE', NULL, '공개 참고자료', 'MEDIUM',
 '현재 최상위권으로 알려진 고추.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- ===== 과자/스낵 (커뮤니티 추정: LOW) =====
(36, '매운새우깡', '농심', 'SNACK', NULL, NULL, 900, '약 900 SHU (추정)',
 FALSE, 'COMMUNITY_ESTIMATE', NULL, '커뮤니티 추정', 'LOW',
 '스낵 중 매운맛 라인.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(37, '불닭볶음탕면 큰컵', '삼양', 'RAMEN', NULL, NULL, 4000, '약 4,000 SHU (추정)',
 FALSE, 'COMMUNITY_ESTIMATE', NULL, '커뮤니티 추정', 'LOW',
 '국물형 불닭 라인.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- ===== 참고용 극단값 =====
(38, '순수 캡사이신', NULL, 'ETC', NULL, NULL, 16000000, '약 16,000,000 SHU',
 FALSE, 'REFERENCE_SITE', NULL, '스코빌 척도 최상단 참고값', 'MEDIUM',
 '식품이 아니며 비교 이해용으로만 표시합니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- ===== 비정형(공식값 없음: UNKNOWN) — 랭킹 제외, 검색·상세에서만 체감 기반 표기 =====
(39, '디진다돈가스', NULL, 'TONKATSU', NULL, NULL, NULL, '공식값 없음',
 FALSE, 'USER_PERCEIVED', NULL, '공식 스코빌 확인 어려움. 사용자 체감 기반 분류.', 'UNKNOWN',
 '공식 스코빌은 확인되지 않았습니다. 후기상 불닭 이상, 핵불닭 이하로 느끼는 사용자가 많습니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(40, '송주불냉면', NULL, 'ETC', NULL, NULL, NULL, '공식값 없음',
 FALSE, 'USER_PERCEIVED', NULL, '공식 스코빌 없음. 사용자 체감 기반.', 'UNKNOWN',
 '매운 냉면으로 유명하나 공식 수치는 없습니다. 체감 기반 참고용입니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 관리자 등록이 기존 id 와 충돌하지 않도록 IDENTITY 시작값을 올린다.
ALTER TABLE spicy_item ALTER COLUMN id RESTART WITH 100;

-- 검색 보조용 별칭
INSERT INTO spicy_alias (item_id, alias) VALUES
(2, '신더레'),
(2, '신라면더레드'),
(8, '불닭'),
(9, '까르보불닭'),
(10, '핵불닭'),
(10, '핵불'),
(15, '엽떡'),
(20, '시라차'),
(33, '고스트페퍼'),
(33, '부트졸로키아'),
(34, '리퍼'),
(39, '디진다');

ALTER TABLE spicy_alias ALTER COLUMN id RESTART WITH 100;
