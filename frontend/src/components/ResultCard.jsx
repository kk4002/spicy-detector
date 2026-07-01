import { useState } from 'react';
import { renderResultCanvas, canvasToBlob } from '../resultImage.js';

// 맵력테스트 결과 카드. 제출 직후/공유 조회 화면에서 공용으로 사용한다.
export default function ResultCard({ result, shareUrl }) {
  const [copied, setCopied] = useState(false);
  const [busy, setBusy] = useState(false);

  const copy = async () => {
    try {
      await navigator.clipboard.writeText(shareUrl);
      setCopied(true);
      setTimeout(() => setCopied(false), 1500);
    } catch (e) {
      // 클립보드 접근 실패 시 무시 (사용자가 직접 복사)
    }
  };

  // 결과를 PNG 이미지로 저장(다운로드)
  const saveImage = async () => {
    setBusy(true);
    try {
      const blob = await canvasToBlob(renderResultCanvas(result));
      const url = URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = `맵력_${result.badgeName}.png`;
      document.body.appendChild(a);
      a.click();
      a.remove();
      URL.revokeObjectURL(url);
    } finally {
      setBusy(false);
    }
  };

  // 공유: 가능하면 이미지 파일까지 공유(모바일), 아니면 텍스트+링크, 최종 폴백은 링크 복사
  const share = async () => {
    setBusy(true);
    try {
      const blob = await canvasToBlob(renderResultCanvas(result));
      const file = new File([blob], '맵력판독.png', { type: 'image/png' });
      const text = `${result.shareText}\n${shareUrl}`;
      if (navigator.canShare && navigator.canShare({ files: [file] })) {
        try {
          await navigator.share({ files: [file], title: '매움판독기', text });
          return;
        } catch (e) { /* 취소/실패 → 폴백 */ }
      }
      if (navigator.share) {
        try {
          await navigator.share({ title: '매움판독기', text: result.shareText, url: shareUrl });
          return;
        } catch (e) { /* 취소/실패 → 폴백 */ }
      }
      copy();
    } finally {
      setBusy(false);
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
          <div className="share-buttons">
            <button onClick={share} className="btn primary" disabled={busy}>
              {busy ? '준비 중...' : '📤 공유하기'}
            </button>
            <button onClick={saveImage} className="btn" disabled={busy}>🖼️ 이미지 저장</button>
            <button onClick={copy} className="btn">{copied ? '복사됨!' : '🔗 링크 복사'}</button>
          </div>
          <div className="share-url">
            <input readOnly value={shareUrl} onFocus={(e) => e.target.select()} />
          </div>
        </div>
      )}
    </div>
  );
}
