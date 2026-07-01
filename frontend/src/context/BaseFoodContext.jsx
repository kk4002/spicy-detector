import { createContext, useContext, useEffect, useState } from 'react';
import { api } from '../api.js';

// 사용자가 선택한 "기준 음식"을 앱 전역에서 공유하기 위한 컨텍스트.
const BaseFoodContext = createContext(null);

const STORAGE_KEY = 'spicy.baseItemId';

export function BaseFoodProvider({ children }) {
  const [baseFoods, setBaseFoods] = useState([]);
  const [baseItemId, setBaseItemId] = useState(() => {
    const saved = localStorage.getItem(STORAGE_KEY);
    return saved ? Number(saved) : null;
  });

  useEffect(() => {
    api.getBaseFoods()
      .then((foods) => {
        setBaseFoods(foods);
        // 저장된 기준이 없으면 첫 번째(가장 순한 라면, 보통 신라면)를 기본값으로
        setBaseItemId((prev) => (prev ?? (foods[0] ? foods[0].id : null)));
      })
      .catch(() => setBaseFoods([]));
  }, []);

  useEffect(() => {
    if (baseItemId != null) localStorage.setItem(STORAGE_KEY, String(baseItemId));
  }, [baseItemId]);

  const baseFood = baseFoods.find((f) => f.id === baseItemId) || null;

  return (
    <BaseFoodContext.Provider value={{ baseFoods, baseItemId, setBaseItemId, baseFood }}>
      {children}
    </BaseFoodContext.Provider>
  );
}

export function useBaseFood() {
  const ctx = useContext(BaseFoodContext);
  if (!ctx) throw new Error('useBaseFood must be used within BaseFoodProvider');
  return ctx;
}
