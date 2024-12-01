import React, { useState } from 'react';
import Password from '../../Components/Fields/Password/password';
import Footer from '../../Components/Footer/footer';
import styles from './signup.module.css';

const Signup = () => {
  const [userName, setUserName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('Signup form submitted:', { userName, email, password, confirmPassword });
  };

  return (
      <div className={styles.backgroundWrapper}>
        <div className={styles.headerContainer}>
          <h1 className={styles.headerTitle}>LiBooks</h1>
          <p className={styles.headerTagline}>Where Bookworms Discover, Review, and Discuss the Stories That Matter</p>
        </div>
    
        <div className={styles.signupContainer}>
          <form onSubmit={handleSubmit} className={styles.signupForm}>
            <div className={styles.formHeader}>
              <h2 className={styles.formTitle}>Create your account</h2>
              <p className={styles.formSubtitle}>Discover, discuss, and dive into stories, books don't lie</p>
            </div>
            <div className={styles.formField}>
              <label>Username:</label>
              <input
                type="text"
                placeholder="John Doe"
                value={userName}
                onChange={(e) => setUserName(e.target.value)}
              />
            </div>
            <div className={styles.formField}>
              <label>Email:</label>
              <input
                type="email"
                placeholder="JohnDoe@gmail.com"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
            </div>
            <div className={styles.formField}>
              <label>Password:</label>
              <Password
                value={password}
                setValue={setPassword}
                placeholder="Enter password"
              />
            </div>
            <div className={styles.formField}>
              <label>Confirm Password:</label>
              <Password
                value={confirmPassword}
                setValue={setConfirmPassword}
                placeholder="Re-enter password"
              />
            </div>
            <button className={styles.googleButton}>
              <img src="/googleLogo.png" alt="Google Logo" />
              Sign up With Google
            </button>
            <div className={styles.acknowledgmentContainer}>
              <p>
                By clicking you agree to LiBooks <a href="/terms">Terms Of Service</a>,
                <a href="/privacy"> Privacy Policy</a>, and
                <a href="/cookies"> Cookie Policy</a>.
              </p>
            </div>
            <button type="submit" className={styles.signupButton}>Create Account</button>
            <div className={styles.signInContainer}>
              <p>Already have an account?</p>
              <a href="#" className={styles.signInLink}>Log in</a>
            </div>
          </form>
        </div>

      <Footer />
    </div>
  );
};

export default Signup;
