import { useNavigate } from 'react-router-dom';

// 결과 화면에서 음식 탐색/랭킹으로 자연스럽게 이어가는 버튼들.
export default function ResultActions({ result }) {
  const navigate = useNavigate();
  // 내 단계에 해당하는 대표 음식(추천 음식 첫 번째)으로 검색을 이어준다.
  const keyword = result.recommended && result.recommended.length > 0 ? result.recommended[0] : null;

  return (
    <div className="result-actions">
      {keyword && (
        <button className="btn" onClick={() => navigate(`/search?keyword=${encodeURIComponent(keyword)}`)}>
          🌶️ 내 단계 비슷한 음식 보기
        </button>
      )}
      <button className="btn" onClick={() => navigate('/ranking')}>📊 매움 랭킹 보기</button>
    </div>
  );
}
