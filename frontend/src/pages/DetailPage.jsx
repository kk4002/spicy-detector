import { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { api } from '../api.js';
import { useBaseFood } from '../context/BaseFoodContext.jsx';
import BaseFoodSelector from '../components/BaseFoodSelector.jsx';
import ConfidenceBadge from '../components/ConfidenceBadge.jsx';

export default function DetailPage() {
  const { id } = useParams();
  const { baseItemId } = useBaseFood();
  const [item, setItem] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    setLoading(true);
    setError('');
    api.getItem(id, baseItemId)
      .then(setItem)
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false));
  }, [id, baseItemId]);

  if (loading) return <p className="muted">불러오는 중...</p>;
  if (error) return <p className="error">{error}</p>;
  if (!item) return null;

  const rel = item.relativeScore;

  return (
    <div className="page detail-page">
      <div className="toolbar">
        <BaseFoodSelector compact />
      </div>

      <div className="detail-head">
        <h2>{item.name}</h2>
        <div className="detail-tags">
          {item.brand && <span className="item-brand">{item.brand}</span>}
          <span className="cat-chip">{item.categoryLabel}</span>
          <ConfidenceBadge level={item.confidenceLevel} />
          {item.officialYn && <span className="official">공식값</span>}
        </div>
      </div>

      <div className="detail-grid">
        <div className="stat">
          <span className="stat-label">스코빌 지수</span>
          <span className="stat-value">{item.shuText || '수치 미상'}</span>
        </div>
        <div className="stat">
          <span className="stat-label">체감 맵기</span>
          <span className="stat-value">{item.perceivedLabel}</span>
        </div>
        {rel && (
          <div className="stat highlight">
            <span className="stat-label">기준 음식 대비</span>
            <span className="stat-value">{rel.text}</span>
          </div>
        )}
      </div>

      {rel && (
        <RelativeBar baseName={rel.baseItemName} scoreMin={rel.scoreMin} scoreMax={rel.scoreMax} />
      )}

      {item.description && <p className="detail-desc">{item.description}</p>}

      {(item.sourceNote || item.sourceType) && (
        <div className="source-box">
          <b>출처/신뢰도 안내</b>
          <p>
            출처 유형: {item.sourceTypeLabel || item.sourceType || '미상'} · 신뢰도: {item.confidenceLevel}
          </p>
          {item.sourceNote && <p className="muted">{item.sourceNote}</p>}
          {item.sourceUrl && (
            <p><a href={item.sourceUrl} target="_blank" rel="noreferrer">출처 링크</a></p>
          )}
          <p className="muted small">제품별·국가별·제조 시점별 차이가 있을 수 있습니다.</p>
        </div>
      )}

      <div className="similar-section">
        <h3>비슷한 매운맛 음식</h3>
        {item.similarItems && item.similarItems.length > 0 ? (
          <div className="similar-list">
            {item.similarItems.map((s) => (
              <Link key={s.id} to={`/items/${s.id}`} className="similar-chip">
                <span>{s.name}</span>
                {s.relativeScore != null && <b>{s.relativeScore}</b>}
              </Link>
            ))}
          </div>
        ) : (
          <p className="muted">비슷한 매운맛으로 분류된 음식이 아직 없습니다.</p>
        )}
      </div>
    </div>
  );
}

// 기준 음식(100) 대비 상대 맵기를 막대로 표현. 200%까지를 화면 폭으로 본다.
function RelativeBar({ baseName, scoreMin, scoreMax }) {
  const cap = 200;
  const toPct = (v) => Math.min(100, (v / cap) * 100);
  const left = toPct(scoreMin);
  const width = Math.max(1.5, toPct(scoreMax) - left);
  return (
    <div className="rel-bar-wrap">
      <div className="rel-bar-track">
        <div className="rel-bar-base" style={{ left: `${toPct(100)}%` }} title={`${baseName} = 100`} />
        <div className="rel-bar-fill" style={{ left: `${left}%`, width: `${width}%` }} />
      </div>
      <div className="rel-bar-legend">
        <span>0</span>
        <span>{baseName}=100</span>
        <span>{cap}+</span>
      </div>
    </div>
  );
}
