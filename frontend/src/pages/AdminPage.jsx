import { useEffect, useState } from 'react';
import { api } from '../api.js';

const EMPTY = {
  name: '', brand: '', category: 'RAMEN',
  shuMin: '', shuMax: '', shuValue: '', shuText: '',
  officialYn: false, sourceType: 'SAMPLE', sourceUrl: '', sourceNote: '',
  confidenceLevel: 'LOW', description: '', aliases: '',
};

// 관리자용 음식 데이터 등록 폼. (MVP: 인증 없음)
export default function AdminPage() {
  const [meta, setMeta] = useState({ categories: [], sourceTypes: [], confidenceLevels: [] });
  const [form, setForm] = useState(EMPTY);
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

  useEffect(() => {
    api.getAdminMeta().then(setMeta).catch((e) => setError(e.message));
  }, []);

  const change = (key) => (e) => {
    const val = e.target.type === 'checkbox' ? e.target.checked : e.target.value;
    setForm((prev) => ({ ...prev, [key]: val }));
  };

  // 문자열 숫자 입력을 정수 또는 null 로 변환
  const num = (v) => (v === '' || v == null ? null : Number(v));

  const submit = (e) => {
    e.preventDefault();
    setMessage('');
    setError('');
    const payload = {
      name: form.name,
      brand: form.brand || null,
      category: form.category,
      shuMin: num(form.shuMin),
      shuMax: num(form.shuMax),
      shuValue: num(form.shuValue),
      shuText: form.shuText || null,
      officialYn: form.officialYn,
      sourceType: form.sourceType || null,
      sourceUrl: form.sourceUrl || null,
      sourceNote: form.sourceNote || null,
      confidenceLevel: form.confidenceLevel || null,
      description: form.description || null,
      aliases: form.aliases
        ? form.aliases.split(',').map((s) => s.trim()).filter(Boolean)
        : [],
    };
    api.createItem(payload)
      .then((saved) => {
        setMessage(`"${saved.name}" 등록 완료 (id=${saved.id})`);
        setForm(EMPTY);
      })
      .catch((e) => setError(e.message));
  };

  return (
    <div className="page admin-page">
      <h2>관리자 · 음식 데이터 등록</h2>
      <p className="muted small">
        정확한 SHU 를 모르는 항목은 공식값처럼 저장하지 마세요.
        출처 유형은 SAMPLE/USER_ESTIMATE, 신뢰도는 LOW/UNKNOWN 을 권장합니다.
      </p>
      {message && <p className="success">{message}</p>}
      {error && <p className="error">{error}</p>}

      <form className="admin-form" onSubmit={submit}>
        <label>음식명 *<input required value={form.name} onChange={change('name')} /></label>
        <label>브랜드<input value={form.brand} onChange={change('brand')} /></label>

        <label>카테고리 *
          <select value={form.category} onChange={change('category')}>
            {meta.categories.map((c) => <option key={c.code} value={c.code}>{c.label}</option>)}
          </select>
        </label>

        <div className="form-row">
          <label>SHU 최소<input type="number" value={form.shuMin} onChange={change('shuMin')} /></label>
          <label>SHU 최대<input type="number" value={form.shuMax} onChange={change('shuMax')} /></label>
          <label>SHU 대표값<input type="number" value={form.shuValue} onChange={change('shuValue')} /></label>
        </div>

        <label>표시 텍스트<input value={form.shuText} onChange={change('shuText')} placeholder="예: 약 4,400 SHU / 공식값 없음" /></label>

        <div className="form-row">
          <label>출처 유형
            <select value={form.sourceType} onChange={change('sourceType')}>
              {meta.sourceTypes.map((s) => <option key={s} value={s}>{s}</option>)}
            </select>
          </label>
          <label>신뢰도
            <select value={form.confidenceLevel} onChange={change('confidenceLevel')}>
              {meta.confidenceLevels.map((c) => <option key={c} value={c}>{c}</option>)}
            </select>
          </label>
          <label className="check">
            <input type="checkbox" checked={form.officialYn} onChange={change('officialYn')} /> 공식값
          </label>
        </div>

        <label>출처 URL<input value={form.sourceUrl} onChange={change('sourceUrl')} /></label>
        <label>출처 메모<input value={form.sourceNote} onChange={change('sourceNote')} /></label>
        <label>별칭(쉼표로 구분)<input value={form.aliases} onChange={change('aliases')} placeholder="예: 불닭, 불닭면" /></label>
        <label>설명<textarea rows={3} value={form.description} onChange={change('description')} /></label>

        <button type="submit" className="btn primary">등록</button>
      </form>
    </div>
  );
}
