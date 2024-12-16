import {Grid, Typography} from "@mui/material";

function BookAdditionalInfo({book}) {
    const {genre, publisher, languageOfOrigin, publicationDate, isbn} = book;
    return (
        <>
            <Grid container spacing={2}>
                <Grid item xs={6}>
                    <Typography variant="subtitle2">Genre</Typography>
                    <Typography variant="body2">{genre}</Typography>
                </Grid>
                <Grid item xs={6}>
                    <Typography variant="subtitle2">Publisher</Typography>
                    <Typography variant="body2">{publisher}</Typography>
                </Grid>
                <Grid item xs={6}>
                    <Typography variant="subtitle2">Language</Typography>
                    <Typography variant="body2">{languageOfOrigin}</Typography>
                </Grid>
                <Grid item xs={6}>
                    <Typography variant="subtitle2">Publication Date</Typography>
                    <Typography variant="body2">
                        {new Date(publicationDate).toLocaleDateString()}
                    </Typography>
                </Grid>
                <Grid item xs={12}>
                    <Typography variant="subtitle2">ISBN</Typography>
                    <Typography variant="body2">{isbn}</Typography>
                </Grid>
            </Grid>
        </>
    )
}

export default BookAdditionalInfo;