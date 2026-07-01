// 신뢰도 등급을 색상 뱃지로 표시. 수치의 신뢰 수준을 한눈에 보여준다.
const LABELS = {
  HIGH: '신뢰도 높음',
  MEDIUM: '신뢰도 보통',
  LOW: '추정값',
  UNKNOWN: '체감 기반',
};

export default function ConfidenceBadge({ level }) {
  const key = level || 'UNKNOWN';
  return (
    <span className={`conf-badge conf-${key.toLowerCase()}`}>
      {LABELS[key] || key}
    </span>
  );
}
