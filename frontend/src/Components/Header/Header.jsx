import './Header.css';
import { HOME, PROFILE } from '../../constants/Constants';
import ProfileAvatar from '../ProfileAvatar/ProfileAvatar';
import { useEffect, useState } from 'react';

const Header = () => {
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");

    const token = localStorage.getItem("token");

    useEffect(() => {
            const getUserName = async () => {
                const response = await fetch(
                        `http://localhost:8080/username`, {
                        method: 'GET',
                        headers: {"Authorization": `Bearer ${token}`},
                    }
                );
                const data = await response.text();
                console.log(data)
                setFirstName(data)
                setLastName(data)
            };
            getUserName();
        }, [firstName,lastName, token]);
    return (
        <div className="header">
            <div className="header-logo">
                <h1>LIBOOKS</h1>
            </div>
            <div className="header-menu">
                <a href={HOME} className="header-link">Home</a>
                <a href={HOME} className="header-link">Books</a>
                <div className="header-user">
                    {/* Take first letter of Username or uploaded photo from user */}
                    <a href={PROFILE} className="header-link">
                        <ProfileAvatar firstName={firstName} lastName={lastName} size="small" />
                    </a>
                </div>
            </div>
        </div>
    );
};

export default Header;