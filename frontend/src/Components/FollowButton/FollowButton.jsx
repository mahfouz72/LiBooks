import React, { useState, useEffect } from "react";
import './FollowButton.css'; // Import the CSS file

function FollowButton({ username, token }) {
    const [isFollowing, setIsFollowing] = useState(false);
    const [currentUsername, setCurrentUsername] = useState(null);

    useEffect(() => {
        const decodeToken = (token) => {
            if (!token) return null;
            const base64Url = token.split('.')[1];
            const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
            const decodedPayload = JSON.parse(window.atob(base64));
            return decodedPayload.sub;
        };

        const fetchFollowStatus = async () => {
            const decodedUsername = decodeToken(token);
            setCurrentUsername(decodedUsername);

            const followingStatus = localStorage.getItem(`${currentUsername}_isFollowing_${username}`);
            if (followingStatus !== null) {
                setIsFollowing(followingStatus === "true");
            } else {
                const response = await fetch(`http://localhost:8080/connection/followings/${username}`, {
                    method: 'GET',
                    headers: { "Authorization": `Bearer ${token}` },
                });
                const data = await response.json();
                const userIsFollowing = data.some(user => user.username === username);
                setIsFollowing(userIsFollowing);
                localStorage.setItem(`${currentUsername}_isFollowing_${username}`, userIsFollowing.toString());
            }
        };

        fetchFollowStatus();
    }, [username, token, currentUsername]);

    const handleFollow = async () => {
        if (currentUsername) {
            await fetch(`http://localhost:8080/connection/follow/${username}/${currentUsername}`, {
                method: 'POST',
                headers: { "Authorization": `Bearer ${token}` },
            });
            setIsFollowing(true);
            // Persist follow status in localStorage
            localStorage.setItem(`${currentUsername}_isFollowing_${username}`, "true");
        }
    };

    const handleUnfollow = async () => {
        if (currentUsername) {
            await fetch(`http://localhost:8080/connection/unfollow/${username}/${currentUsername}`, {
                method: 'DELETE',
                headers: { "Authorization": `Bearer ${token}` },
            });
            setIsFollowing(false);
            // Persist unfollow status in localStorage
            localStorage.setItem(`${currentUsername}_isFollowing_${username}`, "false");
        }
    };

    return (
        <button 
            className={`followButton ${isFollowing ? 'following' : ''}`} 
            onClick={isFollowing ? handleUnfollow : handleFollow}
        >
            {isFollowing ? "Unfollow" : "Follow"}
        </button>
    );
}

export default FollowButton;
