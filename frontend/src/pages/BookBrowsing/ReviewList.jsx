import {Box, Divider, List, ListItem, Rating} from "@mui/material";
import "./ReviewList.css"

function ReviewList({reviews}) {
    return (
        <Box>
            <List>
                {reviews.length > 0 ?
                    (
                        reviews.map((review, index) => (
                            <>
                            <ListItem key={index}>
                                <Box sx={{display: "flex", flexDirection: "column"}}>
                                    <strong>{review.username}</strong>
                                    <div className='rating-header'>
                                        <Rating
                                            name="review-rating"
                                            value={review.rating}
                                            precision={0.5}
                                            readOnly
                                            size="meduim"
                                            sx={{mb: 1}}
                                        />
                                        <p className="review-date">{review.date.slice(0,10)} {review.date.slice(11,19)}</p>
                                    </div>
                                    <p>{review.reviewText}</p>
                                </Box>
                            </ListItem>
                            <Divider sx={{my: 4}}/>
                            </>
                        ))
                    ) : (
                        <p>No reviews yet</p>
                    )}
            </List>
        </Box>
    );
}

export default ReviewList;