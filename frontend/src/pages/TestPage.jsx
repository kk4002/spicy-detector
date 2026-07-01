import { useEffect, useState } from 'react';
import { api } from '../api.js';
import { useBaseFood } from '../context/BaseFoodContext.jsx';
import ResultCard from '../components/ResultCard.jsx';

export default function TestPage() {
  const [questions, setQuestions] = useState([]);
  const [answers, setAnswers] = useState({}); // { questionCode: answerCode }
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState('');
  const { baseItemId } = useBaseFood();

  useEffect(() => {
    api.getQuestions()
      .then(setQuestions)
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false));
  }, []);

  const choose = (qCode, aCode) => {
    setAnswers((prev) => ({ ...prev, [qCode]: aCode }));
  };

  const allAnswered = questions.length > 0 && questions.every((q) => answers[q.code]);

  const submit = () => {
    setSubmitting(true);
    setError('');
    const payload = questions.map((q) => ({ questionCode: q.code, answerCode: answers[q.code] }));
    api.submitTest(payload, baseItemId)
      .then(setResult)
      .catch((e) => setError(e.message))
      .finally(() => setSubmitting(false));
  };

  const restart = () => {
    setAnswers({});
    setResult(null);
    window.scrollTo(0, 0);
  };

  if (loading) return <p className="muted">문항 불러오는 중...</p>;

  if (result) {
    const shareUrl = `${window.location.origin}/test/result/${result.shareToken}`;
    return (
      <div className="page test-page">
        <h2>맵력테스트 결과</h2>
        <ResultCard result={result} shareUrl={shareUrl} />
        <div className="center">
          <button className="btn" onClick={restart}>다시 테스트하기</button>
        </div>
      </div>
    );
  }

  return (
    <div className="page test-page">
      <h2>맵력테스트</h2>
      <p className="muted">질문에 답하면 당신의 맵력 등급과 안전/도전/위험 구간을 판독합니다.</p>
      {error && <p className="error">{error}</p>}

      <div className="question-list">
        {questions.map((q, idx) => (
          <div className="question" key={q.code}>
            <h3>{idx + 1}. {q.text}</h3>
            <div className="options">
              {q.options.map((opt) => (
                <button
                  key={opt.code}
                  className={`option ${answers[q.code] === opt.code ? 'selected' : ''}`}
                  onClick={() => choose(q.code, opt.code)}
                >
                  {opt.label}
                </button>
              ))}
            </div>
          </div>
        ))}
      </div>

      <div className="center">
        <button className="btn primary" disabled={!allAnswered || submitting} onClick={submit}>
          {submitting ? '판독 중...' : '결과 보기'}
        </button>
        {!allAnswered && <p className="muted small">모든 문항에 답해주세요.</p>}
      </div>
    </div>
  );
}
