import { useEffect, useState } from 'react';
import { api } from '../api.js';
import { useBaseFood } from '../context/BaseFoodContext.jsx';
import ResultCard from '../components/ResultCard.jsx';
import ResultActions from '../components/ResultActions.jsx';

export default function TestPage() {
  const [started, setStarted] = useState(false);
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
    setStarted(false);
    window.scrollTo(0, 0);
  };

  // ===== 결과 화면 =====
  if (result) {
    const shareUrl = `${window.location.origin}/test/result/${result.shareToken}`;
    return (
      <div className="page test-page">
        <h2>맵력 판독 결과</h2>
        <ResultCard result={result} shareUrl={shareUrl} />
        <ResultActions result={result} />
        <div className="center">
          <button className="btn primary" onClick={restart}>🔁 다시 테스트하기</button>
        </div>
      </div>
    );
  }

  // ===== 인트로(시작) 화면 =====
  if (!started) {
    return (
      <div className="page test-intro">
        <h2>내 맵력은 몇 단계일까?</h2>
        <p className="intro-sub">
          신라면, 불닭, 핵불닭 기준으로 내가 버틸 수 있는 매운맛을 판독해보세요.
        </p>
        <div className="center">
          <button
            className="btn primary big"
            onClick={() => setStarted(true)}
            disabled={loading || !!error}
          >
            {loading ? '준비 중...' : '맵력 판독 시작하기'}
          </button>
          {error && <p className="error">{error}</p>}
        </div>
      </div>
    );
  }

  // ===== 문항 화면 =====
  const answeredCount = questions.filter((q) => answers[q.code]).length;
  return (
    <div className="page test-page">
      <h2>맵력 판독</h2>
      <div className="progress-line">
        <div className="progress-bar" style={{ width: `${(answeredCount / questions.length) * 100}%` }} />
      </div>
      <p className="muted small">{answeredCount} / {questions.length} 문항 응답</p>
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
        <button className="btn primary big" disabled={!allAnswered || submitting} onClick={submit}>
          {submitting ? '판독 중...' : '결과 보기'}
        </button>
        {!allAnswered && <p className="muted small">모든 문항에 답해주세요.</p>}
      </div>
    </div>
  );
}
