import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// 개발 서버는 /api 요청을 백엔드로 프록시한다.
// - VITE_API_TARGET 환경변수로 백엔드 주소를 바꿀 수 있다(기본 8080).
// - host/allowedHosts 설정으로 임시 터널(외부) 접속을 허용한다.
export default defineConfig({
  plugins: [react()],
  server: {
    host: true,           // 0.0.0.0 바인딩 → 터널/외부에서 접속 가능
    port: 5173,
    allowedHosts: true,   // 임시 터널 도메인(trycloudflare 등) 접속 허용
    proxy: {
      '/api': {
        target: process.env.VITE_API_TARGET || 'http://localhost:8080',
        changeOrigin: true,
      },
    },
  },
});
