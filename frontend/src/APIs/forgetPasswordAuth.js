import axios from 'axios';
import { BACKEND_BASE, FORGETPSSWORD_API } from '../constants/Constants';

axios.defaults.baseURL = BACKEND_BASE;

const forgetPasswordResponse = async (userData) => {
    try {
        const response = await axios.post(FORGETPSSWORD_API, userData);
        return response;

    } catch (error) {
        throw error;
    }
};

export { forgetPasswordResponse };