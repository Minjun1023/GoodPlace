import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './Signup.css';

function Signup() {
    const [username, setUsername] = useState('');
    const [nickname, setNickname] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');

    const [message, setMessage] = useState('');
    const [errors, setErrors] = useState({});
    const [emailVerificationSent, setEmailVerificationSent] = useState(false);

    const handleVerificationRequest = async () => {
        if (!email) {
            setErrors({ email: '이메일을 입력해주세요.' });
            return;
        }
        setErrors({});
        setMessage('인증 메일을 보내는 중...');

        try {
            const response = await fetch('/api/users/email-verification/request', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ email }),
            });

            const responseText = await response.text();
            if (response.ok) {
                setMessage(responseText);
                setEmailVerificationSent(true);
            } else {
                setErrors({ email: responseText });
                setMessage('');
            }
        } catch (err) {
            setErrors({ email: `요청 중 에러 발생: ${err.message}` });
            setMessage('');
        }
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        setMessage('');
        setErrors({});

        if (password !== confirmPassword) {
            setErrors({ confirmPassword: '비밀번호가 일치하지 않습니다.' });
            return;
        }

        try {
            const response = await fetch('/api/users/signup', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, nickname, email, password }),
            });

            if (response.ok) {
                const successMessage = await response.text();
                setMessage(successMessage + " 잠시 후 로그인 페이지로 이동합니다.");
                setTimeout(() => { window.location.href = '/login'; }, 3000);
            } else {
                const contentType = response.headers.get("content-type");
                if (contentType && contentType.indexOf("application/json") !== -1) {
                    const errorData = await response.json();
                    setErrors(errorData);
                } else {
                    const errorText = await response.text();
                    setErrors({ form: errorText });
                }
            }
        } catch (err) {
            setErrors({ form: `요청 중 에러 발생: ${err.message}` });
        }
    };

    return (
        <div className="signup-container">
            <div className="signup-box">
                <h1 className="signup-title">회원가입</h1>
                {message && <p className="success-message">{message}</p>}
                {errors.form && <p className="error-message">{errors.form}</p>}
                
                <form onSubmit={handleSubmit} className="signup-form" noValidate>
                    <div className="input-group">
                        <label htmlFor="email">이메일</label>
                        <div className="email-input-wrapper">
                            <input
                                type="email"
                                id="email"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                placeholder="이메일 주소를 입력하세요"
                                required
                                disabled={emailVerificationSent}
                            />
                            <button type="button" onClick={handleVerificationRequest} disabled={emailVerificationSent} className="verify-button">
                                {emailVerificationSent ? '전송완료' : '인증'}
                            </button>
                        </div>
                        {errors.email && <p className="error-message">{errors.email}</p>}
                    </div>

                    <div className="input-group">
                        <label htmlFor="username">아이디</label>
                        <input type="text" id="username" value={username} onChange={(e) => setUsername(e.target.value)} required />
                        <small className="validation-hint">5-20자의 영문 소문자, 숫자만 사용 가능합니다.</small>
                        {errors.username && <p className="error-message">{errors.username}</p>}
                    </div>

                    <div className="input-group">
                        <label htmlFor="nickname">닉네임</label>
                        <input type="text" id="nickname" value={nickname} onChange={(e) => setNickname(e.target.value)} required />
                        <small className="validation-hint">2-10자로 입력해주세요.</small>
                        {errors.nickname && <p className="error-message">{errors.nickname}</p>}
                    </div>

                    <div className="input-group">
                        <label htmlFor="password">비밀번호</label>
                        <input type="password" id="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
                        <small className="validation-hint">8자 이상, 대/소문자, 숫자, 특수문자(! @#$%^&*) 포함</small>
                        {errors.password && <p className="error-message">{errors.password}</p>}
                    </div>

                    <div className="input-group">
                        <label htmlFor="confirmPassword">비밀번호 확인</label>
                        <input type="password" id="confirmPassword" value={confirmPassword} onChange={(e) => setConfirmPassword(e.target.value)} required />
                        {errors.confirmPassword && <p className="error-message">{errors.confirmPassword}</p>}
                    </div>
                    
                    <button type="submit" className="signup-button">가입하기</button>
                </form>
                <div className="login-link">
                    <p>이미 계정이 있으신가요? <Link to="/login">로그인</Link></p>
                </div>
            </div>
        </div>
    );
}

export default Signup;