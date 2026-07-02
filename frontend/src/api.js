// 백엔드 API 호출 래퍼. 개발 시에는 vite 프록시를 통해 /api 로 전달된다.

const BASE = '/api';

async function request(path, options) {
  const res = await fetch(BASE + path, {
    headers: { 'Content-Type': 'application/json' },
    ...options,
  });
  if (!res.ok) {
    let message = `요청 실패 (${res.status})`;
    try {
      const body = await res.json();
      if (body && body.message) message = body.message;
    } catch (e) {
      // 본문 파싱 실패는 무시하고 기본 메시지 사용
    }
    throw new Error(message);
  }
  // 204 등 본문 없음 대비
  const text = await res.text();
  return text ? JSON.parse(text) : null;
}

export const api = {
  // 음식
  searchItems: (keyword, baseItemId) =>
    request(`/spicy-items/search?keyword=${encodeURIComponent(keyword)}` +
      (baseItemId ? `&baseItemId=${baseItemId}` : '')),
  getItem: (id, baseItemId) =>
    request(`/spicy-items/${id}` + (baseItemId ? `?baseItemId=${baseItemId}` : '')),
  getSimilar: (id, range = 20) =>
    request(`/spicy-items/${id}/similar?range=${range}`),
  getNeighbors: (id, perSide = 5) =>
    request(`/spicy-items/${id}/neighbors?perSide=${perSide}`),
  getRanking: (category, baseItemId) =>
    request(`/spicy-items/ranking` +
      (category ? `?category=${category}` : '?') +
      (baseItemId ? `&baseItemId=${baseItemId}` : '')),
  getBaseFoods: () => request('/spicy-items/base-foods'),
  compareItems: (aId, bId) => request(`/spicy-items/compare?aId=${aId}&bId=${bId}`),

  // 맵력테스트
  getQuestions: () => request('/spicy-test/questions'),
  submitTest: (answers, baseItemId) =>
    request('/spicy-test', {
      method: 'POST',
      body: JSON.stringify({ answers, baseItemId }),
    }),
  getShared: (token) => request(`/spicy-test/share/${token}`),

  // 관리자
  getAdminMeta: () => request('/admin/spicy-items/meta'),
  createItem: (payload) =>
    request('/admin/spicy-items', { method: 'POST', body: JSON.stringify(payload) }),
  updateItem: (id, payload) =>
    request(`/admin/spicy-items/${id}`, { method: 'PUT', body: JSON.stringify(payload) }),
};
