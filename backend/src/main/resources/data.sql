-- ============================================================
-- 매움판독기 초기 샘플 데이터
-- 주의: 대부분의 스코빌 수치는 추정/샘플 값이다.
--       정확한 공식값이 아닌 항목은 official_yn=FALSE,
--       source_type=SAMPLE/ESTIMATE/USER_ESTIMATE,
--       confidence_level=LOW/UNKNOWN 으로 둔다.
-- ddl-auto=create 로 매 기동 시 테이블이 새로 생성되므로 그대로 INSERT 한다.
-- ============================================================

INSERT INTO spicy_item
    (id, name, brand, category, shu_min, shu_max, shu_value, shu_text,
     official_yn, source_type, source_url, source_note, confidence_level, description, created_at, updated_at)
VALUES
-- 1. 신라면 (기준 음식으로 자주 쓰임)
(1, '신라면', '농심', 'RAMEN', NULL, NULL, 2700, '약 2,700 SHU',
 FALSE, 'SAMPLE', NULL, '여러 커뮤니티에서 반복 인용되는 추정값', 'LOW',
 '한국인에게 가장 익숙한 매운맛 기준선. 상대 비교의 기본 기준 음식으로 적합합니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- 2. 신라면 더레드
(2, '신라면 더레드', '농심', 'RAMEN', NULL, NULL, 7500, '약 7,500 SHU',
 FALSE, 'ESTIMATE', NULL, '제품 마케팅 및 후기 기반 추정', 'LOW',
 '기존 신라면보다 훨씬 맵게 나온 버전. 신라면을 편하게 먹어도 체감 차이가 큽니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- 3. 진라면 매운맛
(3, '진라면 매운맛', '오뚜기', 'RAMEN', NULL, NULL, 2000, '약 2,000 SHU',
 FALSE, 'SAMPLE', NULL, '추정값', 'LOW',
 '신라면과 비슷하거나 살짝 순한 편으로 자주 언급됩니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- 4. 열라면
(4, '열라면', '오뚜기', 'RAMEN', NULL, NULL, 5000, '약 5,000 SHU',
 FALSE, 'ESTIMATE', NULL, '후기 기반 추정', 'LOW',
 '신라면보다 확실히 매운 국물 라면. 신라면 다음 단계로 도전하기 좋습니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- 5. 너구리
(5, '너구리', '농심', 'RAMEN', NULL, NULL, 1500, '약 1,500 SHU',
 FALSE, 'SAMPLE', NULL, '추정값', 'LOW',
 '매운맛보다는 얼큰함에 가깝습니다. 순한 편.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- 6. 불닭볶음면
(6, '불닭볶음면', '삼양', 'RAMEN', NULL, NULL, 4404, '약 4,404 SHU',
 FALSE, 'ESTIMATE', NULL, '제조사/미디어에서 다수 인용되는 수치(제품·시점별 차이 가능)', 'MEDIUM',
 '국물 없이 소스로 매운맛이 집중되어 체감 매운맛은 수치 이상으로 느껴질 수 있습니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- 7. 핵불닭볶음면
(7, '핵불닭볶음면', '삼양', 'RAMEN', 8000, 13000, 10000, '약 8,000~13,000 SHU',
 FALSE, 'ESTIMATE', NULL, '범위로 추정', 'LOW',
 '불닭볶음면의 강화 버전. 불닭을 즐기는 사람에게도 확실한 도전 구간입니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- 8. 틈새라면
(8, '틈새라면', '팔도', 'RAMEN', NULL, NULL, 9413, '약 9,413 SHU',
 FALSE, 'ESTIMATE', NULL, '다수 인용되는 추정값', 'MEDIUM',
 '국물 라면 중 최상위권 매운맛으로 유명합니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- 9. 엽기떡볶이 매운맛
(9, '엽기떡볶이 매운맛', '엽기떡볶이', 'TTEOKBOKKI', NULL, NULL, 3000, '약 3,000 SHU',
 FALSE, 'SAMPLE', NULL, '메뉴/매장별 차이가 큼, 추정값', 'LOW',
 '떡볶이 특성상 당도와 소스 양에 따라 체감이 크게 달라집니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- 10. 디진다돈가스 (공식 수치 확인 어려움)
(10, '디진다돈가스', NULL, 'TONKATSU', NULL, NULL, NULL, '공식값 없음',
 FALSE, 'USER_ESTIMATE', NULL, '공식 스코빌 확인 어려움. 사용자 체감 기반 분류.', 'UNKNOWN',
 '공식 스코빌은 확인되지 않았습니다. 사용자 체감 기준으로 불닭 이상, 핵불닭 이하 구간으로 자주 분류됩니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- 11. 청양고추
(11, '청양고추', NULL, 'PEPPER', 4000, 12000, 8000, '약 4,000~12,000 SHU',
 FALSE, 'ESTIMATE', NULL, '개체차가 큼', 'MEDIUM',
 '생고추 중 익숙한 매운맛 기준. 개체에 따라 편차가 큽니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- 12. 할라피뇨
(12, '할라피뇨', NULL, 'PEPPER', 2500, 8000, 5000, '약 2,500~8,000 SHU',
 FALSE, 'ESTIMATE', NULL, '공개 자료 기반 범위', 'MEDIUM',
 '멕시코 요리에 흔히 쓰이는 고추. 청양고추와 비슷하거나 약간 순한 편.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- 13. 하바네로
(13, '하바네로', NULL, 'PEPPER', 100000, 350000, 200000, '약 10만~35만 SHU',
 FALSE, 'ESTIMATE', NULL, '널리 알려진 공개 자료 범위', 'HIGH',
 '매우 강력한 고추. 일반적인 라면류와는 차원이 다른 구간입니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- 14. 타바스코 소스
(14, '타바스코 소스', 'Tabasco', 'HOTSAUCE', 2500, 5000, 3750, '약 2,500~5,000 SHU',
 FALSE, 'ESTIMATE', NULL, '오리지널 레드 기준 공개 자료 범위', 'MEDIUM',
 '대표적인 핫소스. 신맛이 강해 체감 매운맛은 수치보다 낮게 느껴지기도 합니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- 15. 스리라차 소스
(15, '스리라차 소스', 'Huy Fong', 'HOTSAUCE', 1000, 2500, 2000, '약 1,000~2,500 SHU',
 FALSE, 'ESTIMATE', NULL, '공개 자료 기반 범위', 'MEDIUM',
 '달콤·마늘향이 있는 핫소스. 순한 매운맛 구간.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- 16. 순수 캡사이신 (참고용 극단값)
(16, '순수 캡사이신', NULL, 'ETC', NULL, NULL, 16000000, '약 16,000,000 SHU',
 FALSE, 'ESTIMATE', NULL, '순수 캡사이신 결정 기준(참고용 극단값)', 'HIGH',
 '스코빌 척도의 최상단 참고값. 식품이 아니며 비교 이해용으로만 표시합니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 다음 관리자 등록이 기존 id 와 충돌하지 않도록 IDENTITY 시작값을 올린다.
ALTER TABLE spicy_item ALTER COLUMN id RESTART WITH 100;

-- 검색 보조용 별칭
INSERT INTO spicy_alias (item_id, alias) VALUES
(2, '신더레'),
(2, '신라면더레드'),
(6, '불닭'),
(7, '핵불닭'),
(7, '핵불'),
(10, '디진다'),
(9, '엽떡'),
(15, '시라차');

ALTER TABLE spicy_alias ALTER COLUMN id RESTART WITH 100;
