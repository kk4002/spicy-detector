import { useEffect, useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import BaseFoodSelector from '../components/BaseFoodSelector.jsx';
import SpicyItemCard from '../components/SpicyItemCard.jsx';
import { api } from '../api.js';
import { useBaseFood } from '../context/BaseFoodContext.jsx';

export default function HomePage() {
  const [keyword, setKeyword] = useState('');
  const [topItems, setTopItems] = useState([]);
  const navigate = useNavigate();
  const { baseItemId } = useBaseFood();

  // 기본 랭킹(매운순) 미리보기 — 바로 눌러서 상세로 들어갈 수 있게
  useEffect(() => {
    api.getRanking(undefined, baseItemId)
      .then((res) => setTopItems((res.items || []).slice(0, 8)))
      .catch(() => setTopItems([]));
  }, [baseItemId]);

  const submit = (e) => {
    e.preventDefault();
    const k = keyword.trim();
    if (k) navigate(`/search?keyword=${encodeURIComponent(k)}`);
  };

  return (
    <div className="home">
      <section className="hero">
        <h1>매움판독기</h1>
        <p className="tagline">
          신라면은 버티는데, 불닭은 가능할까?<br />
          익숙한 음식 기준으로 매운맛을 판독해보세요.
        </p>

        <form className="hero-search" onSubmit={submit}>
          <input
            type="text"
            value={keyword}
            onChange={(e) => setKeyword(e.target.value)}
            placeholder="음식명을 입력하세요. 예: 신라면, 불닭, 디진다돈가스"
          />
          <button type="submit">검색</button>
        </form>

        <div className="hero-base">
          <BaseFoodSelector />
        </div>

        <div className="hero-actions">
          <button className="btn primary big" onClick={() => navigate('/test')}>🌶️ 내 맵력 판독하기</button>
        </div>
        <p className="hero-sub muted small">위 검색창에서 음식별 매움도 바로 확인할 수도 있어요.</p>
      </section>

      {topItems.length > 0 && (
        <section className="home-ranking">
          <div className="section-head">
            <h3>🔥 매운맛 랭킹</h3>
            <Link to="/ranking" className="more-link">전체 보기 →</Link>
          </div>
          <p className="muted small">눌러서 상세로 들어가고, 상세의 "비슷한 매운맛"으로 계속 타고 넘어가 보세요.</p>
          <div className="item-list">
            {topItems.map((item, idx) => (
              <SpicyItemCard key={item.id} item={item} rank={idx + 1} />
            ))}
          </div>
        </section>
      )}

      <section className="home-note">
        <h3>어떻게 판독하나요?</h3>
        <ul>
          <li><b>스코빌 지수</b> — 참고값으로만 사용합니다.</li>
          <li><b>기준 음식 대비 상대 맵기</b> — 선택한 기준 음식을 100으로 환산합니다.</li>
          <li><b>사용자 체감 맵기</b> — 형태·양·조리 방식에 따른 체감을 함께 봅니다.</li>
        </ul>
      </section>
    </div>
  );
}
