// import { useEffect, useState } from "react";
import "./Profile.css";
import Header from "../../Components/Header/Header";
import BookShelves from "../../Components/BookShelves/BookShelves";
import ProfileAvatar from "../../Components/ProfileAvatar/ProfileAvatar";
import { useEffect, useState } from "react";

function Profile() {
    const [username, setUserName] = useState("")
    const [firstName, setFirstName] = useState("")
    const [lastName, setLastName] = useState("")
    const [isFollowing, setIsFollowing] = useState(false);
    const [followersCount, setFollowersCount] = useState(0);
    const [followingCount, setFollowingCount] = useState(0);

    const followersNumber = 2;
    const followingNumber = 1;

    const token = localStorage.getItem("token");

    useEffect(() => {
        const getUserName = async () => {
            const response = await fetch(
                    `http://localhost:8080/username`, {
                    method: 'GET',
                    headers: {"Authorization": `Bearer ${token}`},
                }
            );
            const data = await response.json();
            setUserName(data.username)
            setFirstName(data.username)
            setLastName(data.username)
            setFollowersCount(data.followersCount);
            setFollowingCount(data.followingCount);
            setIsFollowing(data.isFollowing);
        };
        getUserName();
    }, [username, token]);

    return (
        <div className="profilePage">
            <Header />
            <div className="profileWrapper">
                <div className="profileHeader">
                    {/* Avatar, First Name, Last Name, username, followers and following */}
                    <ProfileAvatar firstName={firstName} lastName={lastName} size="large" />
                    <div className="profileInfo">
                        <h1>{firstName}</h1>
                        <p>@{username}</p>
                        <div className="follows">
                            <button className="followsButton">
                                <span>{followersCount}</span> Followers 
                            </button>
                            <button className="followsButton">
                                <span>{followingCount}</span> Following
                            </button>
                        </div>
                    </div>
                </div>
                <div className="bookShelvesWrapper">
                    <span className="bookShelvesTitle">BookShelves</span>
                    <BookShelves />
                </div>
            </div>
        </div>
    );
}

export default Profile;