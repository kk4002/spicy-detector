import { useEffect, useState } from 'react';
import { useSearchParams, Link } from 'react-router-dom';
import { api } from '../api.js';

// 음식 vs 음식 대결. 두 음식을 골라 몇 배 매운지 비교하고 공유한다.
export default function VersusPage() {
  const [params, setParams] = useSearchParams();
  const [items, setItems] = useState([]);
  const [aId, setAId] = useState(params.get('a') || '');
  const [bId, setBId] = useState(params.get('b') || '');
  const [result, setResult] = useState(null);
  const [error, setError] = useState('');
  const [copied, setCopied] = useState(false);

  // 선택용 음식 목록 (전체 랭킹 = 수치 있는 음식)
  useEffect(() => {
    api.getRanking()
      .then((res) => setItems(res.items || []))
      .catch(() => setItems([]));
  }, []);

  const run = (a, b) => {
    if (!a || !b || a === b) return;
    setError('');
    api.compareItems(a, b)
      .then(setResult)
      .catch((e) => setError(e.message));
  };

  // URL 에 a,b 가 있으면 자동 비교 (공유 링크 진입)
  useEffect(() => {
    if (aId && bId && items.length) run(aId, bId);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [items]);

  const submit = (e) => {
    e.preventDefault();
    setParams({ a: aId, b: bId });
    run(aId, bId);
  };

  const copy = async () => {
    try {
      await navigator.clipboard.writeText(window.location.href);
      setCopied(true);
      setTimeout(() => setCopied(false), 1500);
    } catch (e) { /* 무시 */ }
  };

  return (
    <div className="page versus-page">
      <h2>🥊 매운맛 대결</h2>
      <p className="muted small">두 음식을 골라 누가 더 매운지, 몇 배인지 판독합니다.</p>

      <form className="versus-form" onSubmit={submit}>
        <select value={aId} onChange={(e) => setAId(e.target.value)}>
          <option value="">음식 A 선택</option>
          {items.map((it) => <option key={it.id} value={it.id}>{it.name}</option>)}
        </select>
        <span className="vs-x">VS</span>
        <select value={bId} onChange={(e) => setBId(e.target.value)}>
          <option value="">음식 B 선택</option>
          {items.map((it) => <option key={it.id} value={it.id}>{it.name}</option>)}
        </select>
        <button type="submit" className="btn primary" disabled={!aId || !bId || aId === bId}>대결!</button>
      </form>

      {error && <p className="error">{error}</p>}

      {result && (
        <div className="versus-result">
          <div className="versus-cards">
            <VsCard item={result.a} win={result.spicierName === result.a.name} />
            <div className="vs-mid">VS</div>
            <VsCard item={result.b} win={result.spicierName === result.b.name} />
          </div>
          <p className="versus-verdict">🔥 {result.text}</p>
          <div className="center">
            <button className="btn" onClick={copy}>{copied ? '복사됨!' : '🔗 대결 링크 복사'}</button>
          </div>
        </div>
      )}
    </div>
  );
}

function VsCard({ item, win }) {
  return (
    <Link to={`/items/${item.id}`} className={`vs-card ${win ? 'win' : ''}`}>
      {win && <span className="vs-crown">👑 더 매움</span>}
      <span className="vs-name">{item.name}</span>
      <span className="vs-shu">{item.shuText || '수치 미상'}</span>
    </Link>
  );
}
