import {Box, Divider, Rating, Typography} from "@mui/material";

function BookDetails({book}) {
    const {bookTitle, authors, rating, ratingsCount, summary} = book;
    return (
        <>
            <Typography variant="h4" gutterBottom>
                {bookTitle}
            </Typography>
            <Typography variant="subtitle1" color="textSecondary" gutterBottom>
                by {authors && authors.length > 0 ? authors.join(", ") : "Unknown Author"}
            </Typography>
            <Divider sx={{my: 2}}/>
            <Box display="flex" alignItems="center" gap={1}>
                <Rating value={rating || 0} precision={0.5} readOnly/>
                <Typography variant="body2">({ratingsCount} ratings)</Typography>
            </Box>
            <Divider sx={{my: 2}}/>
            <Typography variant="h6" gutterBottom>
                Summary
            </Typography>
            <Typography variant="body1" paragraph>
                {summary}
            </Typography>
        </>
    );
}

export default BookDetails;