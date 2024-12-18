import React, { useState } from 'react';
import { useGoogleLogin } from '@react-oauth/google'; 
import { useNavigate, Link } from 'react-router-dom';
import { verifyEmail } from '../../APIs/auth';
import { sendVerificationEmail } from '../../APIs/auth';
import styles from './welcome.module.css';
import Footer from '../../Components/Footer/footer';

const GmailSignup = () => {
    const navigate = useNavigate();
    const [email, setEmail] = useState('');
    const [emailError, setEmailError] = useState('');

    const validateEmail = (email) => {
        const emailRegex = /^[a-zA-Z0-9._%+-]+@gmail\.com$/;
        return emailRegex.test(email);
    };

    const handleManualEmailSubmit = async (e) => {
        e.preventDefault();
        setEmailError('');
    
        if (!validateEmail(email)) {
            setEmailError('Please enter a valid Gmail address');
            return;
        }
    
        try {
            await verifyEmail(email);/*Check if email is unique or not*/
            console.log("Email is unique");
            // If email is unique,send the email and navigate to verification page
            await sendVerificationEmail(email);
            console.log("Email sent");
            navigate('/verify-email', { state: { email } });
        } catch (error) {
            if (error.response && error.response.status === 409) {
                setEmailError('This email is already registered');
            } else {
                setEmailError('Unable to verify email at the moment');
            }
        }
    };


    const handleGoogleLogin = useGoogleLogin({
        onSuccess: async (tokenResponse) => {
            try {
                const userInfoResponse = await fetch('https://people.googleapis.com/v1/people/me?personFields=emailAddresses', {
                    headers: {
                        Authorization: `Bearer ${tokenResponse.access_token}`,
                    },
                });
                const userInfo = await userInfoResponse.json();
                const primaryEmail = userInfo.emailAddresses[0].value;

                await verifyEmail(primaryEmail); /*Check if email is unique or not*/
                // Navigate to signup page with Google-retrieved email
                navigate('/signup', { state: { email: primaryEmail } });
            } catch (error) {
                if(error.response && error.response.status === 409){
                    setEmailError('** This email is already registered');
                }
                else{
                    console.error('Failed to fetch Google user info:', error);
                    setEmailError('** Unable to verify email with Google');
                }
            }
        },
        onError: (error) => {
            console.error('Google login failed:', error);
            setEmailError('** Google login failed, please try again');
        },
    });

    const checkEmailUniqueness = async (email) => {
        try{
            const response = await verifyEmail(email);

        }
        catch(error){
            if(error.response && error.response.status === 409){
                setEmailError('** This email is already registered');
                throw new Error(error.response.data);
            }
            throw new Error("Unable to verify email at the moment.");
        }
    }

    return (
        <div className={styles.backgroundWrapper}>
            <div className={styles.headerContainer}>
                <h1 className={styles.headerTitle}>LiBooks</h1>
                <p className={styles.headerTagline}>Where Bookworms Discover, Review, and Discuss the Stories That Matter</p>
            </div>

            <div className={styles.signupContainer}>
                <form 
                    className={styles.signupForm} 
                    onSubmit={handleManualEmailSubmit}
                >
                    <div className={styles.formHeader}>
                        <h2 className={styles.formTitle}>Create your account</h2>
                        <p className={styles.formSubtitle}>Discover, discuss, and dive into stories, books don't lie</p>
                    </div>
                    <div className={styles.formField}>
                        <label>Enter your Gmail:</label>
                        <input
                            type="email"
                            placeholder="example@gmail.com"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                        />
                        {emailError && <p className={styles.errorMessage}>{emailError}</p>}
                    </div>

                    <button type="submit" className={styles.signupButton}>
                        Sign Up with Gmail
                    </button>

                    <div className={styles.orContainer}>
                        <div className={styles.line}></div>
                        <span className={styles.orText}>OR</span>
                        <div className={styles.line}></div>
                    </div>

                    {/* Google Login Option */}
                    <button 
                        type="button" 
                        className={styles.googleButton} 
                        onClick={() => handleGoogleLogin()}
                    >
                        <img src="/googleLogo.png" alt="Google Logo" />
                        Sign up With Google
                    </button>

                    <div className={styles.signInContainer}>
                        <p>Already have an account?</p>
                        <Link to="/login" className={styles.signInLink}>Log in</Link>
                    </div>
                </form>
            </div>

            <Footer />
        </div>
    );
};

export default GmailSignup;