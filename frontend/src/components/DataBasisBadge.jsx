// 데이터 기준을 공식/추정/체감/샘플 4종 뱃지로 표시한다.
const CLASS = {
  '공식': 'basis-official',
  '추정': 'basis-estimate',
  '체감': 'basis-perceived',
  '샘플': 'basis-sample',
};

export default function DataBasisBadge({ basis }) {
  const b = basis || '샘플';
  return <span className={`basis-badge ${CLASS[b] || 'basis-sample'}`}>{b}</span>;
}
