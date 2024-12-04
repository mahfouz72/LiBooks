import {
    Card,
    CardActions,
    CardContent,
    CardMedia,
    Rating,
    Typography,
  } from "@mui/material";
  
  function BookCard({ bookCover, bookTitle, rating, author }) {
    return (
      <Card sx={{ position: "relative", width: 150, height: 350 }}>
        <CardMedia
          component="img"
          image={`data:image/jpeg;base64,${bookCover}`}
          sx={{
            width: "100%",
            height: "200px",
            objectFit: "fill",
          }}
          title="book"
        />
        <CardContent>
          <Typography
            gutterBottom
            variant="subtitle1"
            component="div"
            color="primary"
            fontWeight="bold"
            sx={{
              whiteSpace: 'nowrap', overflow: 'hidden',textOverflow: 'ellipsis'
            }}
          >
            {bookTitle}
          </Typography>
          <Typography variant="body2" sx={{ color: "black" }}>
            by {author}
          </Typography>
        </CardContent>
        <CardActions sx={{position: 'absolute' , bottom: 0}}>
          <Rating
            name="half-rating-read"
            defaultValue={rating}
            precision={0.5}
            readOnly
          />
        </CardActions>
      </Card>
    );
  }
  export default BookCard;
  