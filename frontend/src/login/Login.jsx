import React from "react";
import './Login.css'
import { LuUserCircle2 } from "react-icons/lu";
import { MdLock } from "react-icons/md";


function LoginForm(){
    return(
        <div className="wrapper">
            <form action="">
                <h1>Login</h1>
                <div className="input-box">
                    <input type="text" placeholder="username" required/>
                </div>
                <div className="input-box">
                    <input type="password" placeholder="password" required/>
                </div>
                <div className="rememberme-forget">
                    <label htmlFor=""><input type="checkbox"/>Remember me</label>
                    <a href="#">Forget password</a>
                </div>
                <button className="login-btn" type="submit">Log in</button>
                <button className="google-btn"><span>Continue with Google</span></button>
                <div className="register">
                    <p>Don't have an account? <a href="#">Register</a></p>
                </div>
            </form>
        </div>
    )
}

export default LoginForm