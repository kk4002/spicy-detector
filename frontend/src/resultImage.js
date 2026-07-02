// 맵력테스트 결과를 공유용 PNG 카드로 렌더링한다.
// 외부 라이브러리/모델 없이 Canvas 2D 로 즉시 그린다(오프라인 가능).

import { levelTheme } from './levelTheme.js';

const KO_FONT = "'Apple SD Gothic Neo','Malgun Gothic','Noto Sans KR',sans-serif";

function roundRect(ctx, x, y, w, h, r) {
  ctx.beginPath();
  ctx.moveTo(x + r, y);
  ctx.arcTo(x + w, y, x + w, y + h, r);
  ctx.arcTo(x + w, y + h, x, y + h, r);
  ctx.arcTo(x, y + h, x, y, r);
  ctx.arcTo(x, y, x + w, y, r);
  ctx.closePath();
}

/** 결과 데이터를 받아 canvas 를 그려 반환한다. */
export function renderResultCanvas(result) {
  const W = 800;
  const H = 1000;
  const canvas = document.createElement('canvas');
  canvas.width = W;
  canvas.height = H;
  const ctx = canvas.getContext('2d');
  const theme = levelTheme(result.level);

  // 배경 그라데이션
  const bg = ctx.createLinearGradient(0, 0, 0, H);
  bg.addColorStop(0, '#ff7a29');
  bg.addColorStop(1, '#e23b2e');
  ctx.fillStyle = bg;
  ctx.fillRect(0, 0, W, H);

  // 흰 패널
  ctx.fillStyle = '#ffffff';
  roundRect(ctx, 44, 44, W - 88, H - 88, 36);
  ctx.fill();

  ctx.textAlign = 'center';

  // 상단 타이틀
  ctx.fillStyle = '#8a817c';
  ctx.font = `bold 32px ${KO_FONT}`;
  ctx.fillText('🔥 나의 맵력 판독 결과', W / 2, 130);

  // 레벨 뱃지 원
  const cx = W / 2;
  const cy = 250;
  const r = 78;
  const cg = ctx.createLinearGradient(cx - r, cy - r, cx + r, cy + r);
  cg.addColorStop(0, '#ff7a29');
  cg.addColorStop(1, '#e23b2e');
  ctx.fillStyle = cg;
  ctx.beginPath();
  ctx.arc(cx, cy, r, 0, Math.PI * 2);
  ctx.fill();
  // 레벨 이모지
  ctx.font = `56px ${KO_FONT}`;
  ctx.fillText(theme.emoji, cx, cy - 4);
  ctx.fillStyle = '#ffffff';
  ctx.font = `800 30px ${KO_FONT}`;
  ctx.fillText(`Lv.${result.level}`, cx, cy + 40);

  // 뱃지명
  ctx.fillStyle = '#e23b2e';
  ctx.font = `800 58px ${KO_FONT}`;
  ctx.fillText(result.badgeName, W / 2, 400);

  // 밈 문구
  if (theme.meme) {
    ctx.fillStyle = '#8a817c';
    ctx.font = `italic 28px ${KO_FONT}`;
    ctx.fillText(`“${theme.meme}”`, W / 2, 444);
  }

  // 구간 3개
  const zones = [
    ['안전 구간', result.safeZone, '#2f9e57'],
    ['도전 구간', result.challengeZone, '#e8901a'],
    ['위험 구간', result.dangerZone, '#d1372b'],
  ];
  let y = 470;
  zones.forEach(([label, val, color]) => {
    ctx.fillStyle = color;
    roundRect(ctx, 96, y, W - 192, 92, 18);
    ctx.fill();
    ctx.textAlign = 'left';
    ctx.fillStyle = 'rgba(255,255,255,0.85)';
    ctx.font = `bold 24px ${KO_FONT}`;
    ctx.fillText(label, 128, y + 36);
    ctx.fillStyle = '#ffffff';
    ctx.font = `800 34px ${KO_FONT}`;
    ctx.fillText(clip(ctx, val, W - 260), 128, y + 74);
    y += 110;
  });

  // 예상 맵력
  if (result.toleranceMin != null && result.toleranceMax != null) {
    ctx.textAlign = 'center';
    ctx.fillStyle = '#23201f';
    ctx.font = `bold 26px ${KO_FONT}`;
    ctx.fillText(
      `예상 맵력: 신라면=100 기준 약 ${result.toleranceMin}~${result.toleranceMax}`,
      W / 2, y + 44
    );
  }

  // 푸터
  ctx.textAlign = 'center';
  ctx.fillStyle = '#8a817c';
  ctx.font = `bold 28px ${KO_FONT}`;
  ctx.fillText('🌶️ 매움판독기', W / 2, H - 90);
  ctx.fillStyle = '#b9b0aa';
  ctx.font = `500 22px ${KO_FONT}`;
  ctx.fillText('신라면은 버티는데, 불닭은 가능할까?', W / 2, H - 56);

  return canvas;
}

// 텍스트가 최대폭을 넘으면 말줄임 처리
function clip(ctx, text, maxWidth) {
  if (ctx.measureText(text).width <= maxWidth) return text;
  let t = text;
  while (t.length > 1 && ctx.measureText(t + '…').width > maxWidth) {
    t = t.slice(0, -1);
  }
  return t + '…';
}

/** canvas 를 PNG Blob 으로 변환 (Promise) */
export function canvasToBlob(canvas) {
  return new Promise((resolve) => canvas.toBlob(resolve, 'image/png'));
}
