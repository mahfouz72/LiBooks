import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { BACKEND_BASE } from "../../constants/Constants";
import { LOGIN_API } from "../../constants/Constants";
import { HOME } from "../../constants/Constants";
import { loginWithGoogle } from "../../APIs/auth"; 
import { useGoogleLogin } from '@react-oauth/google';
import Password from '../../Components/Fields/Password/password';

import '../../styles/form.css'

function LoginForm(){

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [usernameError, setUsernameError] = useState("");
    const [passwordError, setPasswordError] = useState("");
    const [errorMessage, setErrorMessage] = useState("");

    const navigate = useNavigate();

    const handleSubmit = async () => {
        setUsernameError("");
        setPasswordError("");
        setErrorMessage("");

        if(username === ''){
            setUsernameError("Username is required!");
            return
        }

        if(username.length < 2){
            setUsernameError("The username must be at least 3 characters");
            return
        }

        if(password === ''){
            setPasswordError("Password is required!");
            return
        }

        if(password.length < 7){
            setPasswordError("The password must be at least 8 characters");
            return
        }
        
        if(password.length > 50){
            setPasswordError("The password must be at most 50 characters");
            return;
        }

        console.log("Login attempt with:", { username, password });

        try{
            const response = await fetch(BACKEND_BASE + LOGIN_API+`?username=${username}&password=${password}`,{
                method: "POST",
                headers: {"Content-Type": "application/json"},
            });
    
            if(response.status === 200){
                const token = await response.text();
                localStorage.setItem("token", token); //to be improved later
                console.log("Login successful");
                navigate(HOME);
            }
            else{
                const errorMessage = await response.text();
                console.log(errorMessage);
                setErrorMessage("Invalid username or password");
            }
        }catch(error){
            console.log("something went wrong");
        }
    };

    const handleLoginGoogle = useGoogleLogin({
        onSuccess: async (tokenResponse) => {
            const googleToken = tokenResponse.access_token;
                
            try {
                const response = await loginWithGoogle(googleToken);
                console.log(response);
                if(response){
                    localStorage.setItem("token", response);
                    navigate(HOME);
                }else{
                    setErrorMessage("Your Gmail is not registered, please sign up");
                }
            } catch (error) {
                console.log(error)
                console.error("Error authenticating with Google:", error);
                setErrorMessage("Authentication failed. Please try again.");
            }
        },
        onError: (error) => {
            console.error("Google login failed:", error);
            setErrorMessage("Login failed. Please try again.");
        },
    });

    const handleLoginClick = () => {
        handleLoginGoogle();
    };

    return(
        <div className="box-wrapper">
        <div className="wrapper">
            <form action="">
                <h1>Login</h1>

                <div className="input-box">
                    <input type="text" 
                        placeholder="username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />
                    <p className="error">{usernameError}</p>
                </div>

                <div className="input-box">
                    <Password
                        value={password}
                        setValue={setPassword}
                        placeholder="Password"
                    />
                    <p className="error">{passwordError}</p>
                </div>

                <div className="rememberme-forget">
                    <label htmlFor=""><input type="checkbox"/>Remember me</label>
                    <Link to="/forgetPassword">Forget password</Link>
                </div>
                <p className="error">{errorMessage}</p>
                <button className="login-btn" type="button" onClick={handleSubmit}>Log in</button>
                <button className="google-btn" type="button" onClick={handleLoginClick}><span>Continue with Google</span></button>
                <div className="register">
                    <p>Don't have an account? <Link to="/register">Register</Link></p>
                </div>
            </form>
        </div>
        </div>
    )
}

export default LoginForm;