import axios from 'axios';
import { BACKEND_BASE, RESETPASSWORD_API } from '../constants/Constants';

axios.defaults.baseURL = BACKEND_BASE;

const resetPasswordRequest = async (password, token) => {
    try {
        const response = await axios.post(BACKEND_BASE + RESETPASSWORD_API +`?password=${password}&token=${token}`,{
            method: "POST",
            headers: {"Content-Type": "application/json"},
        });
        return response;

    } catch (error) {
        throw error;
    }
};

export { resetPasswordRequest };