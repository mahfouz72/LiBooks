import axios from 'axios';

// Configure base URL
axios.defaults.baseURL = 'http://localhost:8080';

const registerUser = async (userSignupData) => {
  try {
    const response = await axios.post('/register', userSignupData);
    return response.data; 
  } catch (error) {
    throw error; 
  }
};

const loginWithGoogle = async (token) => {
  try {
    console.log("token:", token);
    const response = await axios.post('/login/google', { token });
    return response.data;
  } catch (error) {
    throw error;
  }
};

const verifyEmail = async (email) => {
  try {
    const response = await axios.get('/register/request', { 
      params: { email } 
    });
    return response.data;
  } catch (error) {
    throw error;
  } 
};

const sendVerificationEmail = async (email) => {
  try{
    await axios.post('/verification/sendEmail', { email });
  }catch(error){
    throw error;
  }
};

const verifyEmailCode = async ({ email, code }) => {
  try {
    const response = await axios.post('/verification/code', { email, code });
    return response.data;
  } catch (error) {
    throw error;
  }
};


export { sendVerificationEmail, verifyEmailCode, verifyEmail, registerUser, loginWithGoogle };