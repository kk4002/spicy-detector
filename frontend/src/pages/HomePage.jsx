import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import BaseFoodSelector from '../components/BaseFoodSelector.jsx';

export default function HomePage() {
  const [keyword, setKeyword] = useState('');
  const navigate = useNavigate();

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
          <button className="btn primary" onClick={() => navigate('/test')}>맵력테스트 시작하기</button>
          <button className="btn" onClick={() => navigate('/search')}>음식 검색하기</button>
          <button className="btn" onClick={() => navigate('/ranking')}>매운맛 랭킹 보기</button>
        </div>
      </section>

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
