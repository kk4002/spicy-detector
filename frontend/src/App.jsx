import { Routes, Route, Link, NavLink } from 'react-router-dom';
import HomePage from './pages/HomePage.jsx';
import SearchPage from './pages/SearchPage.jsx';
import DetailPage from './pages/DetailPage.jsx';
import RankingPage from './pages/RankingPage.jsx';
import TestPage from './pages/TestPage.jsx';
import TestResultPage from './pages/TestResultPage.jsx';
import AdminPage from './pages/AdminPage.jsx';

export default function App() {
  return (
    <div className="app">
      <header className="app-header">
        <Link to="/" className="logo">🌶️ 매움판독기</Link>
        <nav className="app-nav">
          <NavLink to="/test">맵력판독</NavLink>
          <NavLink to="/search">음식검색</NavLink>
          <NavLink to="/ranking">매움랭킹</NavLink>
          <NavLink to="/admin" className="nav-admin">관리자</NavLink>
        </nav>
      </header>

      <main className="app-main">
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/search" element={<SearchPage />} />
          <Route path="/items/:id" element={<DetailPage />} />
          <Route path="/ranking" element={<RankingPage />} />
          <Route path="/test" element={<TestPage />} />
          <Route path="/test/result/:token" element={<TestResultPage />} />
          <Route path="/admin" element={<AdminPage />} />
        </Routes>
      </main>

      <footer className="app-footer">
        <p>스코빌 지수는 참고값입니다. 형태·양·조리 방식에 따라 체감은 달라질 수 있으며, 출처가 없는 수치는 추정값입니다.</p>
      </footer>
    </div>
  );
}
