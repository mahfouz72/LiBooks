import { Grid2, Card, CardMedia, Typography } from "@mui/material";

function AuthorList({ authors, sx }) { 

    const renderedAuthors = authors.map(({ id, authorName, authorPhoto }) => (
        <Grid2 key={id}>
            <Card sx={{ padding: 2, width: 300 }}>
                <CardMedia
                    component="img"
                    image={`data:image/jpeg;base64,${authorPhoto}`}
                    sx={{ width: "100%", height: "150px", objectFit: "cover" }}
                />
                <Typography variant="h6" sx={{ marginBottom: 1 }}>
                    {authorName}
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
