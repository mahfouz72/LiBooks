import { Grid2, Card, CardMedia, Typography } from "@mui/material";

function AuthorList({ authors, sx }) {
    const truncateText = (text, maxLength) => {
        if (text.length > maxLength) {
            return `${text.substring(0, maxLength)}...`;
        }
        return text;
    };

    const renderedAuthors = authors.map(({ id, name, authorPicture, bio }) => (
        <Grid2 key={id}>
            <Card sx={{ padding: 2, width: 300 }}>
                <CardMedia
                    component="img"
                    image={`data:image/jpeg;base64,${authorPicture}`}
                    sx={{ width: "100%", height: "150px", objectFit: "cover" }}
                />
                <Typography variant="h6" sx={{ marginBottom: 1 }}>
                    {name}
                </Typography>
                <Typography variant="body2" sx={{ color: "gray" }}>
                    {truncateText(bio, 100)} {/* Truncate bio to 100 characters */}
                </Typography>
            </Card>
        </Grid2>
    ));

    return (
        <Grid2 container spacing={4} sx={sx}>
            {renderedAuthors}
        </Grid2>
    );
}

export default AuthorList;
