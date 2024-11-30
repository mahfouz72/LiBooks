import {Card, CardActions, CardContent, CardMedia, Rating, Typography} from "@mui/material";

function BookCard({bookCover,title,author,rating}){
    return(
        <Card sx={{ maxWidth: 345 }}>
            <CardMedia
                component="img"
                alt="book"
                sx={{
                    width: "auto",
                    height: "auto",
                    maxWidth: "100%",
                    margin: "auto",
                    display: "block",
                }}
                image={bookCover}
                title="book"
            />
            <CardContent>
                <Typography gutterBottom variant="h5" component="div">
                    {title}
                </Typography>
                <Typography variant="body2" sx={{ color: 'text.secondary' }}>
                    by {author}
                </Typography>
            </CardContent>
            <CardActions>
                <Rating name="half-rating-read" defaultValue={rating} precision={0.5} readOnly />
            </CardActions>
        </Card>
    )
}
export default BookCard;