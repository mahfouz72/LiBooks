import {Card, CardActions, CardContent, CardMedia, Rating, Typography} from "@mui/material";

function BookCard({bookCover,title,author,rating}){
    return(
        <Card sx={{ width: 150}}>
            <CardMedia
                component="img"
                image={bookCover}
                sx={{
                    width: "100%",
                    height: "200px",
                    objectFit: 'fill',
                }}
                title="book"
            />
            <CardContent>
                <Typography gutterBottom variant="h5" component="div" color="primary" fontWeight="bold">
                    {title}
                </Typography>
                <Typography variant="body2" sx={{ color: 'black' }}>
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