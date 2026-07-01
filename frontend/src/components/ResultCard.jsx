import { useState } from 'react';

// 맵력테스트 결과 카드. 제출 직후/공유 조회 화면에서 공용으로 사용한다.
export default function ResultCard({ result, shareUrl }) {
  const [copied, setCopied] = useState(false);

  const copy = async () => {
    try {
      await navigator.clipboard.writeText(shareUrl);
      setCopied(true);
      setTimeout(() => setCopied(false), 1500);
    } catch (e) {
      // 클립보드 접근 실패 시 무시 (사용자가 직접 복사)
    }
  };

  return (
    <div className="result-card">
      <div className="badge-emblem">Lv.{result.level}</div>
      <h2 className="badge-name">{result.badgeName}</h2>
      <p className="result-summary">{result.summary}</p>

      <div className="zone-grid">
        <div className="zone safe">
          <span className="zone-label">안전 구간</span>
          <span className="zone-value">{result.safeZone}</span>
        </div>
        <div className="zone challenge">
          <span className="zone-label">도전 구간</span>
          <span className="zone-value">{result.challengeZone}</span>
        </div>
        <div className="zone danger">
          <span className="zone-label">위험 구간</span>
          <span className="zone-value">{result.dangerZone}</span>
        </div>
      </div>

      {(result.toleranceMin != null && result.toleranceMax != null) && (
        <p className="tolerance">
          예상 맵력: 신라면=100 기준 약 {result.toleranceMin}~{result.toleranceMax}
        </p>
      )}

      <div className="reco-grid">
        <div>
          <b>추천 음식</b>
          <ul>{(result.recommended || []).map((r) => <li key={r}>{r}</li>)}</ul>
        </div>
        <div>
          <b>피해야 할 음식</b>
          <ul>{(result.avoid || []).map((r) => <li key={r}>{r}</li>)}</ul>
        </div>
      </div>

      {shareUrl && (
        <div className="share-box">
          <p className="share-text">{result.shareText}</p>
          <div className="share-row">
            <input readOnly value={shareUrl} onFocus={(e) => e.target.select()} />
            <button onClick={copy}>{copied ? '복사됨!' : '링크 복사'}</button>
          </div>
        </div>
      )}
    </div>
  );
}
