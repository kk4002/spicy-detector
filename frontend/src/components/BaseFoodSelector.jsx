import { useBaseFood } from '../context/BaseFoodContext.jsx';

// 기준 음식 선택 드롭다운. 선택 값은 전역 컨텍스트에 저장되어 모든 상대 비교에 적용된다.
export default function BaseFoodSelector({ compact }) {
  const { baseFoods, baseItemId, setBaseItemId } = useBaseFood();

  return (
    <div className={`base-selector ${compact ? 'compact' : ''}`}>
      <label htmlFor="base-food">기준 음식</label>
      <select
        id="base-food"
        value={baseItemId ?? ''}
        onChange={(e) => setBaseItemId(Number(e.target.value))}
      >
        {baseFoods.length === 0 && <option value="">불러오는 중...</option>}
        {baseFoods.map((f) => (
          <option key={f.id} value={f.id}>
            {f.name}
          </option>
        ))}
      </select>
      <span className="base-hint">= 100 기준</span>
    </div>
  );
}
