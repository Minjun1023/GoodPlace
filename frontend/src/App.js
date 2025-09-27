import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Link, Navigate } from 'react-router-dom';
import './App.css';
import Signup from './pages/Signup'; // 회원가입 페이지 컴포넌트 import
import VerifyEmail from './pages/VerifyEmail'; // 이메일 인증 페이지 컴포넌트 import

// --- 컴포넌트 분리 ---

// 1. 헤더 컴포넌트
function AppHeader({ user }) {
    return (
        <header className="app-header">
            <Link to="/" className="logo">GoodPlace</Link>
            <div className="user-info">
                {user ? (
                    <>
                        <p>{user.nickname}님</p>
                        <a href="http://localhost:8080/api/users/logout" className="logout">로그아웃</a>
                    </>
                ) : (
                    <Link to="/login" className="login-btn">로그인</Link>
                )}
            </div>
        </header>
    );
}

// 2. 로그인 페이지 컴포넌트
function LoginPage() {
    return (
        <main className="main-container">
            <div className="login-page">
                <h1>로그인</h1>

                {/* 일반 로그인 폼 (기능은 구현되지 않음) */}
                <form className="login-form">
                    <input type="text" placeholder="아이디" className="login-input" />
                    <input type="password" placeholder="비밀번호" className="login-input" />
                    <button type="submit" className="login-submit">로그인</button>
                </form>

                {/* 회원가입 / 아이디 찾기 / 비밀번호 찾기 */}
                <div className="login-links">
                    <Link to="/signup">회원가입</Link>
                    {/* <a href="/find-id">아이디 찾기</a>
                    <a href="/find-password">비밀번호 찾기</a> */}
                </div>

                <hr className="divider" />

                {/* 소셜 로그인 */}
                <p style={{ textAlign: "center", marginBottom: "15px" }}>
                    소셜 로그인
                </p>
                <div className="login-buttons">
                    <a href="http://localhost:8080/oauth2/authorization/google" className="google">Google 계정으로 로그인</a>
                    <a href="http://localhost:8080/oauth2/authorization/naver" className="naver">Naver 계정으로 로그인</a>
                    <a href="http://localhost:8080/oauth2/authorization/kakao" className="kakao">Kakao 계정으로 로그인</a>
                </div>
            </div>
        </main>
    );
}

// 3. 메인 페이지 컴포넌트
function MainPage() {
    const [activeTab, setActiveTab] = useState('total');
    const [searchQuery, setSearchQuery] = useState('');
    const [searchResults, setSearchResults] = useState([]); // 검색 결과를 담을 상태

    const handleSearch = () => {
        if (!searchQuery) {
            alert('검색어를 입력해주세요!');
            return;
        }
        fetch(`http://localhost:8080/api/v1/search?query=${searchQuery}`, { credentials: 'include' })
            .then(response => {
                if (!response.ok) throw new Error('검색에 실패했습니다.');
                return response.json();
            })
            .then(data => {
                setSearchResults(data.items || []);
            })
            .catch(error => {
                console.error("검색 오류:", error);
                alert(error.message);
            });
    };

    const regions = ['내 주변', '성수', '홍대', '청담동/압구정', '판교', '수원 광교', '전주 한옥마을', '제주', '부산 해운대', '강릉'];
    const foodTypes = ['평양냉면', '파스타', '모던한식', '프랑스음식', '빙수', '삼계탕', '한우 오마카세', '숙성 스시'];

    return (
        <main className="main-container">
            <div className="content-wrapper">
                <div className="search-box">
                    <h2>어떤 맛집을 찾으시나요?</h2>
                    <div className="search-tabs">
                        <button className={activeTab === 'total' ? 'active' : ''} onClick={() => setActiveTab('total')}>
                            통합검색
                        </button>
                        <button className={activeTab === 'restaurant' ? 'active' : ''} onClick={() => setActiveTab('restaurant')}>
                            식당명 검색
                        </button>
                    </div>
                    <div className="search-input-wrapper">
                        <input
                            type="text"
                            placeholder="검색어를 입력해주세요"
                            value={searchQuery}
                            onChange={(e) => setSearchQuery(e.target.value)}
                            onKeyUp={(e) => e.key === 'Enter' && handleSearch()}
                        />
                        <span className="search-icon" onClick={handleSearch}>🔍</span>
                    </div>
                </div>

                {searchResults.length > 0 && (
                    <section className="category-section">
                        <h2>검색 결과</h2>
                        <ul className="result-list">
                            {searchResults.map((item, index) => {
                                const title = item.title.replace(/<[^>]*>?/gm, '');
                                return (
                                    <li key={index}>
                                        <div>
                                            <b>{title}</b><br/><small>{item.address}</small>
                                        </div>
                                        <button className="add-btn">추가</button>
                                    </li>
                                );
                            })}
                        </ul>
                    </section>
                )}

                <section className="category-section">
                    <div className="category-header">
                        <h2>어디로 갈까요?</h2>
                        <a href="#" className="view-more">더보기</a>
                    </div>
                    <div className="category-grid">
                        {regions.map(region => (<div key={region} className="category-item">{region}</div>))}
                    </div>
                </section>

                <section className="category-section">
                    <div className="category-header">
                        <h2>무엇을 먹을까요?</h2>
                        <a href="#" className="view-more">더보기</a>
                    </div>
                    <div className="category-grid">
                        {foodTypes.map(food => (<div key={food} className="category-item">{food}</div>))}
                    </div>
                </section>

                <section className="category-section">
                    <div className="ai-section">
                        <h2>결정이 어렵다면?</h2>
                        <p>AI에게 직접 물어보고 맛집을 추천받아 보세요!</p>
                        <button>AI한테 음식점 추천받기</button>
                    </div>
                </section>
            </div>
        </main>
    );
}

// 4. 최상위 App 컴포넌트 (상태 및 라우팅 관리)
function App() {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetch('http://localhost:8080/api/users/me', { credentials: 'include' })
            .then(response => response.ok ? response.json() : null)
            .then(data => {
                setUser(data);
                setLoading(false);
            })
            .catch(() => setLoading(false));
    }, []);

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <Router>
            <div className="App">
                <AppHeader user={user} />
                <Routes>
                    <Route path="/" element={<MainPage />} />
                    <Route path="/login" element={user ? <Navigate to="/" /> : <LoginPage />} />
                    <Route path="/signup" element={user ? <Navigate to="/" /> : <Signup />} />
                    <Route path="/verify-email" element={<VerifyEmail />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;