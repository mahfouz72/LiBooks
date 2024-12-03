import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate, Link } from 'react-router-dom';
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

  const handleSubmit = (e) => {
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

    // Clear errors
    setFormError('');
    setPasswordError('');

    // Create JSON object with user information
    const userSignupData = {
      username: userName,
      email: email,
      password: password
    };

    // Log the object (replace with actual backend call)
    console.log('User Signup Data:', userSignupData);

    // Here you would typically send userSignupData to your backend
    // Example: 
    // axios.post('/api/signup', userSignupData)
    //   .then(response => {
    //     // Handle successful signup
    //   })
    //   .catch(error => {
    //     // Handle signup error
    //   });
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
            {passwordError && <p className={styles.errorMessage}>{passwordError}</p>}
          </div>

          {formError && <p className={styles.errorMessage}>{formError}</p>}

          <button type="submit" className={styles.signupButton}>Create Account</button>

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

export default Signup;