import React, { useState } from "react";
import { Link, useParams } from "react-router-dom";
import Password from '../../Components/Fields/Password/password';
import {resetPasswordRequest } from "../../APIs/resetPasswordAuth";
import '../../styles/form.css'

function ResetPasswordForm(){

    const {token} = useParams();
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [confirmMessage, setConfirmMessage] = useState("");
    const [errorMessage, setErrorMessage] = useState("");



    const handleSubmit = async () => {
        setErrorMessage("");
        setConfirmMessage("");

        if(password === ''){
            setErrorMessage("Password is required!");
            return;
        }

        if(password.length < 7){
            setErrorMessage("The password must be at least 8 characters");
            return;
        }

        if(password.length > 128){
            setErrorMessage("The password must be at most 128 characters");
            return;
        }

        if (!(password === confirmPassword)) {
            setErrorMessage('Passwords do not match');
            return;
        }


        try {
            const response = await resetPasswordRequest(password, token);

            if(response.status === 200){
                console.log("Password was reset successfully");
                setConfirmMessage("Password was reset successfully")
            }
            else{
                const errorMessage = await response.text();
                console.log(errorMessage);
                setErrorMessage("Reset link is expired, Try to send another reset request");
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
                <h1>Reset Password</h1>

                <div className="input-box">
                <Password
                    value={password}
                    setValue={setPassword}
                    placeholder="New Password"
                />
                </div>

                <div className="input-box">
                <Password
                    value={confirmPassword}
                    setValue={setConfirmPassword}
                    placeholder="Confirm new password"
                />
                </div>
                <p className="error">{errorMessage}</p>
                <p className="confirm">{confirmMessage}</p>
                <button type="button" className="btn" 
                    onClick={handleSubmit}
                    disabled={!!confirmMessage}
                    >Confirm</button>
                {confirmMessage && (
                    <div className="register">
                        <p>Back to <Link to="/login">login</Link></p>
                    </div>
                )}
            </form>
        </div>
        </div>
    )
}

export default ResetPasswordForm;