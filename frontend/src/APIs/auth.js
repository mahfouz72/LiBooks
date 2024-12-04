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

export { registerUser, loginWithGoogle };