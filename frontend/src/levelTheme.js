// 맵력 레벨별 이모지/밈 문구. 결과 카드(화면·이미지)에서 공용 사용.
const THEMES = {
  1: { emoji: '😇', meme: '매운 건 사양할게요' },
  2: { emoji: '🍜', meme: '신라면 정돈 문제없지' },
  3: { emoji: '🌶️', meme: '이 정도는 즐긴다' },
  4: { emoji: '🔥', meme: '불닭 앞에서 고민 중' },
  5: { emoji: '😤', meme: '불닭? 이미 클리어' },
  6: { emoji: '🥵', meme: '핵불닭 생존 신고' },
  7: { emoji: '⚔️', meme: '매운맛과 싸우는 자' },
  8: { emoji: '💀', meme: '위장을 갈아버리는 자' },
};

export function levelTheme(level) {
  return THEMES[level] || { emoji: '🌶️', meme: '' };
}
