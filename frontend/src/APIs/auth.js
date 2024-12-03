import axios from 'axios';

// Configure base URL
axios.defaults.baseURL = 'http://localhost:8081';

const registerUser = async (userSignupData) => {
  try {
    const response = await axios.post('/register', userSignupData);
    return response.data; 
  } catch (error) {
    throw error; 
  }
};

export { registerUser };