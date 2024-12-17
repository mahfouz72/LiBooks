import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate, Link } from 'react-router-dom';
import { registerUser } from '../../APIs/auth';
import Password from '../../Components/Fields/Password/password';
import Footer from '../../Components/Footer/footer';
import styles from './signup.module.css';

const Signup = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const [userName, setUserName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [formError, setFormError] = useState('');
  const [passwordError, setPasswordError] = useState('');

  // Redirect protection and email retrieval
  useEffect(() => {
    // Check if email was passed from previous page
    if (!location.state || !location.state.email) {
      // If no email in state, redirect to registration page
      navigate('/register');
      return;
    }

    // Set email from state
    setEmail(location.state.email);
  }, [location.state, navigate]);

  const fieldsSigned = () => {
    return userName && password && confirmPassword;
  };

  const checkPasswordsMatch = () => {
    return password === confirmPassword;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Check that all fields are filled
    if (!fieldsSigned()) {
      setFormError('** Please fill out all fields');
      return;
    }

    // Check if passwords match
    if (!checkPasswordsMatch()) {
      setPasswordError('** Passwords do not match');
      return;
    }

    if(password.length < 7){
      setPasswordError("The password must be at least 8 characters");
      return;
    }
    if(password.length > 50){
      setPasswordError("The password must be at most 50 characters");
      return;
    }

    // Clear errors
    setFormError('');
    setPasswordError('');

    // Create JSON object with user information
    const userSignupData = {
      username: userName,
      email: email,
      password: password,
    };

    try {
      const userData = await registerUser(userSignupData); 
      console.log('User successfully registered:', userData);
      navigate('/BookBrowsingPage'); // Example route after successful registration
    } catch (error) {
      console.error('Registration failed:', error.response ? error.response.data : error.message);
      setFormError('Registration failed. Please try again.');
    }
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
            <input
              type="text"
              placeholder="Username"
              value={userName}
              onChange={(e) => setUserName(e.target.value)}
            />
          </div>

          <div className={styles.formField}>
            <Password
              value={password}
              setValue={setPassword}
              placeholder="Enter password"
            />
          </div>
          <div className={styles.formField}>
            <Password
              value={confirmPassword}
              setValue={setConfirmPassword}
              placeholder="Re-enter password"
            />
            {passwordError && <p className={styles.errorMessage}>{passwordError}</p>}
          </div>

          {formError && <p className={styles.errorMessage}>{formError}</p>}

          <button type="submit" className={styles.signupButton}>Create Account</button>

        </form>
      </div>

      <Footer />
    </div>
  );
};

export default Signup;
