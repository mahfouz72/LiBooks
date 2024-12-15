// import { useEffect, useState } from "react";
import "./Profile.css";
import Header from "../../Components/Header/Header";
import BookShelves from "../../Components/BookShelves/BookShelves";
import ProfileAvatar from "../../Components/ProfileAvatar/ProfileAvatar";

function Profile() {

    const firstName = "Youssif";
    const lastName = "Khaled";
    const username = "youssifkhaled";
    const followersNumber = 2;
    const followingNumber = 1;

    return (
        <div className="profilePage">
            <Header />
            <div className="profileWrapper">
                <div className="profileHeader">
                    {/* Avatar, First Name, Last Name, username, followers and following */}
                    <ProfileAvatar firstName={firstName} lastName={lastName} size="large" />
                    <div className="profileInfo">
                        <h1>{firstName} {lastName}</h1>
                        <p>@{username}</p>
                        <div className="follows">
                            <button className="followsButton">
                                <span>{followersNumber}</span> Followers 
                            </button>
                            <button className="followsButton">
                                <span>{followingNumber}</span> Following
                            </button>
                        </div>
                    </div>
                </div>
                <div className="bookShelvesWrapper">
                    {/* Bookshelf */}
                    <span className="bookShelvesTitle">BookShelves</span>
                    <BookShelves />
                </div>
            </div>
        </div>
    );
}

export default Profile;