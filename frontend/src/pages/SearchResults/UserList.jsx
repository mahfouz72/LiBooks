import React from 'react';
import { Grid2, Card, CardMedia, Typography } from "@mui/material";

function UserList({ users, sx }) {
    const renderedUsers = users.map(({ id, username }) => (
        <Grid2 key={id}>
            <Card sx={{ padding: 2, width: 300 }}>
                <Typography variant="h6">{username}</Typography>
            </Card>
        </Grid2>
    ));

    return (
        <Grid2 container spacing={4} sx={sx}>
            {renderedUsers}
        </Grid2>
    );
}

export default UserList;