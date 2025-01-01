import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import "../profile/Profile.css";
import Header from "../../Components/Header/Header";
import BookShelves from "../../Components/BookShelves/BookShelves";
import ProfileAvatar from "../../Components/ProfileAvatar/ProfileAvatar";
import FollowButton from "../../Components/FollowButton/FollowButton";

function Bookworm() {
    const { username } = useParams(); // Get the username from the URL
    const [profileData, setProfileData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [firstName, setFirstName] = useState("")
    const [lastName, setLastName] = useState("")
    const [isFollowing, setIsFollowing] = useState(false);
    const [followersCount, setFollowersCount] = useState(0);
    const [followingCount, setFollowingCount] = useState(0);

    const token = localStorage.getItem("token");

    useEffect(() => {
        const fetchProfileData = async () => {
            try {
                const response = await fetch(`http://localhost:8080/bookworms/${username}`, {
                    method: 'GET',
                    headers: {
                        "Authorization": `Bearer ${token}`,
                        "Content-Type": "application/json"
                    },
                });

                if (response.ok) {
                    const data = await response.json();
                    setProfileData(data);
                    setFirstName(data.username)
                    setLastName(data.username)
                    setFollowersCount(data.followersCount);
                    setFollowingCount(data.followingCount);
                    setIsFollowing(data.isFollowing);
                } else {
                    const errorMessage = `Error: ${response.status}`;
                    setError(errorMessage);
                }
            } catch (err) {
                setError("Error fetching profile data.");
            } finally {
                setLoading(false);
            }
        };

        fetchProfileData();
    }, [username, token]);

    if (loading) {
        return <div>Loading profile...</div>;
    }

    if (error) {
        return <div className="errorMessage">{error}</div>;
    }

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
                                <span>{profileData.followersCount}</span> Followers
                            </button>
                            <button className="followsButton">
                                <span>{profileData.followingCount}</span> Following
                            </button>
                        </div>
                        {/* Add FollowButton component */}
                        <FollowButton username={username} token={token} />
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

export default Bookworm;
