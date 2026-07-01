import { useEffect, useState } from 'react';
import { api } from '../api.js';
import { useBaseFood } from '../context/BaseFoodContext.jsx';
import BaseFoodSelector from '../components/BaseFoodSelector.jsx';
import SpicyItemCard from '../components/SpicyItemCard.jsx';

const CATEGORIES = [
  { code: '', label: '전체' },
  { code: 'RAMEN', label: '라면' },
  { code: 'TTEOKBOKKI', label: '떡볶이' },
  { code: 'TONKATSU', label: '돈가스' },
  { code: 'HOTSAUCE', label: '핫소스' },
  { code: 'PEPPER', label: '고추' },
  { code: 'SNACK', label: '과자/스낵' },
  { code: 'ETC', label: '기타' },
];

export default function RankingPage() {
  const [category, setCategory] = useState('');
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const { baseItemId } = useBaseFood();

  useEffect(() => {
    setLoading(true);
    setError('');
    api.getRanking(category || undefined, baseItemId)
      .then((res) => setItems(res.items || []))
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false));
  }, [category, baseItemId]);

  return (
    <div className="page ranking-page">
      <h2>매운맛 랭킹</h2>
      <div className="toolbar">
        <BaseFoodSelector compact />
      </div>

      <div className="cat-tabs">
        {CATEGORIES.map((c) => (
          <button
            key={c.code || 'all'}
            className={`cat-tab ${category === c.code ? 'active' : ''}`}
            onClick={() => setCategory(c.code)}
          >
            {c.label}
          </button>
        ))}
      </div>

      {loading && <p className="muted">불러오는 중...</p>}
      {error && <p className="error">{error}</p>}

      <div className="item-list">
        {items.map((item, idx) => (
          <SpicyItemCard key={item.id} item={item} rank={idx + 1} />
        ))}
      </div>
    </div>
  );
}
