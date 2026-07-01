import { Link } from 'react-router-dom';
import DataBasisBadge from './DataBasisBadge.jsx';
import { useBaseFood } from '../context/BaseFoodContext.jsx';
import { formatRelative } from '../format.js';

// 검색 결과/랭킹에서 사용하는 음식 카드.
export default function SpicyItemCard({ item, rank }) {
  const { baseFood } = useBaseFood();
  const baseName = baseFood ? baseFood.name : '기준';

  return (
    <Link to={`/items/${item.id}`} className="item-card">
      {rank != null && <div className="rank-num">{rank}</div>}
      <div className="item-main">
        <div className="item-title-row">
          <span className="item-name">{item.name}</span>
          {item.brand && <span className="item-brand">{item.brand}</span>}
          <span className="cat-chip">{item.categoryLabel}</span>
        </div>
        <div className="item-meta">
          <span className="shu-text">{item.shuText || '수치 미상'}</span>
          {item.perceivedLabel && <span className="perceived">체감 {item.perceivedLabel}</span>}
        </div>
      </div>
      <div className="item-side">
        {item.relativeScore != null && (
          <div className="rel-score" title={`${baseName}을(를) 100으로 봤을 때의 상대 지수`}>
            <b>{formatRelative(item.relativeScore)}</b>
            <small>{baseName} 기준(=100)</small>
          </div>
        )}
        <DataBasisBadge basis={item.dataBasis} />
      </div>
    </Link>
  );
}
