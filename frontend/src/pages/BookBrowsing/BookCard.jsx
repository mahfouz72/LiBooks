import {
    Card,
    CardActions,
    CardContent,
    CardMedia,
    Rating,
    Typography,
  } from "@mui/material";
import {useNavigate} from "react-router-dom";
  
  function BookCard({bookId, bookCover, bookTitle, rating, author }) {
       const navigate = useNavigate();

       function handleBookClick(){
            navigate(`/book/${bookId}`);
       }


    return (
      <Card sx={{ position: "relative", width: 150, height: 350, cursor: "pointer",}}
            title={bookTitle}
            onClick={handleBookClick}>
        <CardMedia
          component="img"
          image={`data:image/jpeg;base64,${bookCover}`}
          sx={{
            width: "100%",
            height: "200px",
            objectFit: "fill",
          }}
          title={bookTitle}
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
  