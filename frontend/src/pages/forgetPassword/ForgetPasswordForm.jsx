import React, { useState } from "react";
import { Link } from "react-router-dom";
import { forgetPasswordRequest } from "../../APIs/forgetPasswordAuth";
import '../../styles/form.css'

function ForgetPasswordForm(){

    const [email, setEmail] = useState("");
    const [confirmMessage, setConfirmMessage] = useState("");
    const [errorMessage, setErrorMessage] = useState("");


    function isValidEmail(email) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    }

    const handleSubmit = async () => {
        setErrorMessage("");
        setConfirmMessage("");

        if(!isValidEmail(email)){
            setErrorMessage("Enter a valid email");
            return;
        }


        const userData = {
            email: email,
        };


        try {
            const response = await forgetPasswordRequest(userData);

            if(response.status === 200){
                console.log("Email to reset password was sent successfully");
                setConfirmMessage("Email to reset password was sent successfully")
            }
            else{
                const errorMessage = await response.text();
                console.log(errorMessage);
                setErrorMessage("Email not found");
            }

        } catch (error) {
            console.log("something went wrong");
            setErrorMessage("Something went wrong, Try again later!");
        }

    };

    return(
        <div className="box-wrapper">
        <div className="wrapper">
            <form action="">
                <h1>Forget Password</h1>

                <div className="input-box">
                    <input type="text" 
                        placeholder="Email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                </div>

                <p className="error">{errorMessage}</p>
                <p className="confirm">{confirmMessage}</p>
                <button type="button" className="btn" 
                    onClick={handleSubmit}
                    disabled={!!confirmMessage}
                    >Confirm</button>
                <div className="register">
                    <p>Back to login? <Link to="/login">login</Link></p>
                </div>
            </form>
        </div>
        </div>
    )
}

export default ForgetPasswordForm;