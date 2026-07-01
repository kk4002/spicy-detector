import { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { api } from '../api.js';
import ResultCard from '../components/ResultCard.jsx';
import ResultActions from '../components/ResultActions.jsx';

// 공유 링크로 진입했을 때 저장된 결과를 보여주는 화면.
export default function TestResultPage() {
  const { token } = useParams();
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    api.getShared(token)
      .then(setResult)
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false));
  }, [token]);

  if (loading) return <p className="muted">불러오는 중...</p>;
  if (error) return (
    <div className="page">
      <p className="error">{error}</p>
      <Link className="btn" to="/test">내 맵력 테스트하기</Link>
    </div>
  );

  const shareUrl = `${window.location.origin}/test/result/${token}`;

  return (
    <div className="page test-page">
      <h2>공유된 맵력 결과</h2>
      <ResultCard result={result} shareUrl={shareUrl} />
      <ResultActions result={result} />
      <div className="center">
        <Link className="btn primary" to="/test">나도 맵력 테스트하기</Link>
      </div>
    </div>
  );
}
