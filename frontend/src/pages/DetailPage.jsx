import { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { api } from '../api.js';
import { useBaseFood } from '../context/BaseFoodContext.jsx';
import BaseFoodSelector from '../components/BaseFoodSelector.jsx';
import DataBasisBadge from '../components/DataBasisBadge.jsx';
import { formatRelative } from '../format.js';
import { getTolerance, verdictFor } from '../tolerance.js';

// 기준 음식(=100) 대비 상대 지수로 도전 난이도 구간을 판정한다.
function challengeLevel(score) {
  if (score == null) return null;
  if (score <= 110) return { label: '안전 구간', cls: 'zone-safe' };
  if (score <= 180) return { label: '도전 구간', cls: 'zone-challenge' };
  return { label: '위험 구간', cls: 'zone-danger' };
}

export default function DetailPage() {
  const { id } = useParams();
  const { baseItemId, baseFood } = useBaseFood();
  const [item, setItem] = useState(null);
  const [neighbors, setNeighbors] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    setLoading(true);
    setError('');
    api.getItem(id, baseItemId)
      .then(setItem)
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false));
    // 이 음식 기준 위/아래 순위 (전역 랭킹과 별개인 로컬 순위)
    api.getNeighbors(id, 5)
      .then((res) => setNeighbors(res.items || []))
      .catch(() => setNeighbors([]));
  }, [id, baseItemId]);

  if (loading) return <p className="muted">불러오는 중...</p>;
  if (error) return <p className="error">{error}</p>;
  if (!item) return null;

  const rel = item.relativeScore;
  const challenge = rel ? challengeLevel(rel.scoreMax) : null;

  // 내 맵력(테스트 결과) 기반 개인화 판독
  const myTol = getTolerance();
  const myVerdict = verdictFor(item.shinRelative, myTol);

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
          <DataBasisBadge basis={item.dataBasis} />
          {item.officialYn && <span className="official">공식값</span>}
          {challenge && (
            <span className={`challenge-tag ${challenge.cls}`}>
              {baseFood ? `${baseFood.name} 기준 ` : ''}{challenge.label}
            </span>
          )}
        </div>
      </div>

      {myVerdict ? (
        <div className={`my-verdict ${myVerdict.cls}`}>
          <span className="mv-badge">내 맵력: {myTol.badge}</span>
          <span className="mv-label">{myVerdict.label}</span>
          <span className="mv-desc">{myVerdict.desc}</span>
        </div>
      ) : (
        <div className="my-verdict none">
          <span className="mv-desc">내 맵력을 알면 "이거 먹을 수 있나?"를 판독해드려요.</span>
          <Link to="/test" className="mv-cta">맵력 판독하기 →</Link>
        </div>
      )}

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

      {neighbors.length > 1 && (
        <div className="neighbor-section">
          <h3>🌡️ {item.name} 기준 매운맛 순위</h3>
          <p className="muted small">위로 갈수록 더 맵고, 아래로 갈수록 덜 맵습니다. ({item.name} = 100)</p>
          <div className="neighbor-list">
            {neighbors.map((n) => (
              n.anchor ? (
                <div key={n.id} className="neighbor-row anchor">
                  <span className="n-name">{n.name}</span>
                  <span className="n-rel">100</span>
                </div>
              ) : (
                <Link key={n.id} to={`/items/${n.id}`} className="neighbor-row">
                  <span className="n-name">{n.name}</span>
                  <span className="n-cat">{n.categoryLabel}</span>
                  <span className="n-rel">{formatRelative(n.relativeToAnchor)}</span>
                </Link>
              )
            ))}
          </div>
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
