import React, { useEffect, useState } from 'react';
import { Grid2, Card, Typography } from "@mui/material";
import { useNavigate } from "react-router-dom";
import ProfileAvatar from "../../Components/ProfileAvatar/ProfileAvatar";

function UserList({ users, sx }) {

    const navigate = useNavigate();
    const token = localStorage.getItem("token");
    const [filteredUsers, setFilteredUsers] = useState(users);

    const removeCurrentUser = (token) => {
        if (!token) return;
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const decodedPayload = JSON.parse(window.atob(base64));
        const currentUsername = decodedPayload.sub;

        const updatedUsers = users.filter(user => user.username !== currentUsername);
        setFilteredUsers(updatedUsers);
    };

    const handleCardClick = (username) => {
        navigate(`/${username}`);
    };

    const renderedUsers = filteredUsers.map(({ id, username }) => (
        <Grid2 key={id}>
            <Card sx={{ cursor: "pointer", padding: 2, width: 300, display: 'flex', flexDirection: 'row', gap: '1rem', alignItems: 'center' }} onClick={() => handleCardClick(username)}>
                <ProfileAvatar firstName={username} lastName={username} size="small" />
                <Typography variant="h6">{username}</Typography>
            </Card>
        </Grid2>
    ));

    useEffect(() => {
        removeCurrentUser(token);
    }, [token, users]);

    return (
        <Grid2 container spacing={4} sx={sx}>
            {renderedUsers}
        </Grid2>
    );
}

export default UserList;