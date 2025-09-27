import React, { useState, useEffect } from 'react';
import { useLocation, Link } from 'react-router-dom';
import './VerifyEmail.css';

function VerifyEmail() {
    const [message, setMessage] = useState('이메일 인증을 확인하는 중입니다...');
    const location = useLocation();

    useEffect(() => {
        const params = new URLSearchParams(location.search);
        const token = params.get('token');

        if (token) {
            fetch(`/api/users/email-verification/verify?token=${token}`)
                .then(response => response.text())
                .then(text => setMessage(text))
                .catch(err => setMessage(`인증 중 에러가 발생했습니다: ${err.message}`));
        } else {
            setMessage('유효하지 않은 인증 링크입니다.');
        }
    }, [location]);

    return (
        <div className="verify-email-container">
            <div className="verify-email-box">
                <h1 className="verify-email-title">이메일 인증</h1>
                <p className="verify-email-message">{message}</p>
                {message.includes('성공') && (
                    <Link to="/login" className="login-link-button">로그인 페이지로 이동</Link>
                )}
            </div>
        </div>
    );
}

export default VerifyEmail;
