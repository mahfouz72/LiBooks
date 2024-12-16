import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { verifyEmailCode } from '../../APIs/auth';
import { sendVerificationEmail } from '../../APIs/auth';
import styles from './emailVerification.module.css';
import Footer from '../../Components/Footer/footer';

const EmailVerification = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const [verificationCode, setVerificationCode] = useState(['', '', '', '', '', '']);
    const [error, setError] = useState('');
    const [email, setEmail] = useState('');

    useEffect(() => {
        // Check if email was passed from previous page
        if (!location.state || !location.state.email) {
            // If no email in state, redirect to registration page
            navigate('/register');
            return;
        }
        setEmail(location.state.email);
    }, [location.state, navigate]);

    const handleCodeChange = (index, value) => {
        // Validate input is a single digit
        if (/^\d$/.test(value) || value === '') {
            const newCode = [...verificationCode];
            newCode[index] = value;
            setVerificationCode(newCode);
            // Automatically move to next input if digit is entered
            if (value && index < 5) {
                document.getElementById(`code-input-${index + 1}`).focus();
            }
        }
    };

    const handleVerification = async (e) => {
        e.preventDefault();
        
        // Check if all code fields are filled
        if (verificationCode.some(digit => digit === '')) {
            setError('Please enter the complete verification code');
            return;
        }
        
        const code = verificationCode.join('');
        try {
            // Verify email code
            await verifyEmailCode({ email, code });
            
            // If verification is successful, navigate to signup page
            navigate('/signup', { 
                state: { 
                    email: email,
                    emailVerified: true 
                } 
            });
        } catch (error) {
            // Handle verification failure
            if (error.response && error.response.status === 401) {
                setError('Invalid or expired verification code. Please try again.');
            } else {
                setError('An error occurred. Please try again later.');
            }
        }
    };

    const handleResendEmail = async (e) => {
        e.preventDefault();
            
        try {
            await sendVerificationEmail(email);
            console.log("Email sent");
        } catch (error) {
            setError('Unable to verify email at the moment');
        }
    };

    return (
        <div className={styles.backgroundWrapper}>
            <div className={styles.headerContainer}>
                <h1 className={styles.headerTitle}>LiBooks</h1>
            </div>

            <div className={styles.verificationContainer}>
                <form onSubmit={handleVerification} className={styles.verificationForm}>
                    <div className={styles.formHeader}>
                        <h2 className={styles.formTitle}>Verify Your Email</h2>
                        <p className={styles.formSubtitle}>
                            Enter the 6-digit verification code sent to {email}
                        </p>
                    </div>

                    <div className={styles.codeInputContainer}>
                        {verificationCode.map((digit, index) => (
                            <input
                                key={index}
                                id={`code-input-${index}`}
                                type="text"
                                maxLength="1"
                                value={digit}
                                onChange={(e) => handleCodeChange(index, e.target.value)}
                                className={styles.codeInput}
                            />
                        ))}
                    </div>

                    {error && <p className={styles.errorMessage}>{error}</p>}

                    <button type="submit" className={styles.verifyButton}>
                        Verify Code
                    </button>

                    <div className={styles.resendContainer}>
                        <p>Didn't receive the code?</p>
                        <button 
                            type="button" 
                            onClick={handleResendEmail} 
                            className={styles.resendLink}
                        >
                            Resend Code
                        </button>
                    </div>
                </form>
            </div>

            <Footer />
        </div>
    );
};

export default EmailVerification;