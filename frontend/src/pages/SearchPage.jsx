import { useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import { api } from '../api.js';
import { useBaseFood } from '../context/BaseFoodContext.jsx';
import BaseFoodSelector from '../components/BaseFoodSelector.jsx';
import SpicyItemCard from '../components/SpicyItemCard.jsx';

export default function SearchPage() {
  const [params, setParams] = useSearchParams();
  const initial = params.get('keyword') || '';
  const [keyword, setKeyword] = useState(initial);
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [searched, setSearched] = useState(false);
  const { baseItemId } = useBaseFood();

  const runSearch = (k) => {
    const q = (k ?? keyword).trim();
    if (!q) return;
    setLoading(true);
    setError('');
    setSearched(true);
    api.searchItems(q, baseItemId)
      .then((res) => setItems(res.items || []))
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false));
  };

  // URL 의 keyword 또는 기준 음식이 바뀌면 재검색
  useEffect(() => {
    if (initial) runSearch(initial);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [initial, baseItemId]);

  const submit = (e) => {
    e.preventDefault();
    setParams(keyword.trim() ? { keyword: keyword.trim() } : {});
    runSearch();
  };

  return (
    <div className="page search-page">
      <h2>음식 검색</h2>
      <div className="toolbar">
        <BaseFoodSelector compact />
      </div>

      <form className="search-box" onSubmit={submit}>
        <input
          type="text"
          value={keyword}
          onChange={(e) => setKeyword(e.target.value)}
          placeholder="음식명을 입력하세요. 예: 신라면, 불닭, 디진다돈가스"
          autoFocus
        />
        <button type="submit">검색</button>
      </form>

      {loading && <p className="muted">검색 중...</p>}
      {error && <p className="error">{error}</p>}
      {!loading && searched && items.length === 0 && !error && (
        <p className="muted">검색 결과가 없습니다. 다른 키워드로 시도해보세요.</p>
      )}

      <div className="item-list">
        {items.map((item) => (
          <SpicyItemCard key={item.id} item={item} />
        ))}
      </div>
    </div>
  );
}
