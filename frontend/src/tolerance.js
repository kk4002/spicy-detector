// 사용자의 맵력(테스트 결과)을 로컬에 저장/조회한다.
// 신라면=100 기준의 예상 맵력 범위(min~max)와 뱃지 정보를 담는다.
const KEY = 'spicy.myTolerance';

export function saveTolerance(result) {
  if (!result || result.toleranceMin == null) return;
  const data = {
    min: result.toleranceMin,
    max: result.toleranceMax,
    level: result.level,
    badge: result.badgeName,
  };
  localStorage.setItem(KEY, JSON.stringify(data));
}

export function getTolerance() {
  try {
    const raw = localStorage.getItem(KEY);
    return raw ? JSON.parse(raw) : null;
  } catch (e) {
    return null;
  }
}

// 신라면=100 기준 상대 지수(shinRelative)를 내 맵력과 비교해 판정한다.
export function verdictFor(shinRelative, tol) {
  if (shinRelative == null || !tol) return null;
  if (shinRelative <= tol.min * 0.9) {
    return { label: '여유롭게 가능', cls: 'zone-safe', desc: '당신 맵력엔 순한 편이에요.' };
  }
  if (shinRelative <= tol.max) {
    return { label: '도전 가능', cls: 'zone-challenge', desc: '컨디션 좋으면 도전해볼 만해요.' };
  }
  if (shinRelative <= tol.max * 1.4) {
    return { label: '아슬아슬', cls: 'zone-challenge', desc: '당신 한계선 근처입니다. 주의!' };
  }
  return { label: '위험', cls: 'zone-danger', desc: '당신 맵력 기준 무리한 구간이에요.' };
}
