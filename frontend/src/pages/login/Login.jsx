import React, { useState } from "react";
import { Link } from "react-router-dom";
import './login.css'

function LoginForm(){
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [usernameError, setUsernameError] = useState("");
    const [passwordError, setPasswordError] = useState("");

    const handleSubmit = () => {
        setUsernameError("");
        setPasswordError("");

        if(username == ''){
            setUsernameError("Username is required!");
            return
        }

        if(username.length < 2){
            setUsernameError("The username must be at least 3 characters");
            return
        }

        if(password == ''){
            setPasswordError("Password is required!");
            return
        }

        if(password.length < 7){
            setPasswordError("The password must be at least 8 characters");
            return
        }

        // Add your login logic here
        console.log("Login attempt with:", { username, password });
    };

    return(
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
                    <input type="password" 
                        placeholder="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    <p className="error">{passwordError}</p>
                </div>

                <div className="rememberme-forget">
                    <label htmlFor=""><input type="checkbox"/>Remember me</label>
                    <a href="#">Forget password</a>
                </div>
                <button className="login-btn" type="button" onClick={handleSubmit}>Log in</button>
                <button className="google-btn"><span>Continue with Google</span></button>
                <div className="register">
                    <p>Don't have an account? <Link to="/register">Register</Link></p>
                </div>
            </form>
        </div>
    )
}

export default LoginForm;