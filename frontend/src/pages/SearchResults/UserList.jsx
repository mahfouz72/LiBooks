import {Grid2} from "@mui/material";


function UserList({ users, sx }) {
    const renderedUsers = users.map(({ id, username, profilePicture }) => (
        <Grid2 key={id}>
            <Card sx={{ padding: 2, width: 300 }}>
                <CardMedia
                    component="img"
                    image={`data:image/jpeg;base64,${profilePicture}`}
                    sx={{ width: "100%", height: "150px", objectFit: "cover" }}
                />
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