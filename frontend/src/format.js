// 상대 지수 표시 포맷.
// 기준 음식 = 100 이므로 100 근처는 지수 그대로, 아주 큰 값(>=1000, 즉 10배 이상)은
// "약 N배"로 humanize 해서 정체불명의 큰 숫자가 그대로 노출되지 않게 한다.
export function formatRelative(score) {
  if (score == null) return '';
  if (score < 1000) return String(score);
  return `약 ${Math.round(score / 100).toLocaleString()}배`;
}
