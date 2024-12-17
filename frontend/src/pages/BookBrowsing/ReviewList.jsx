import {Box, Divider, List, ListItem, Rating} from "@mui/material";

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
                                    <Rating
                                        name="review-rating"
                                        value={review.rating}
                                        precision={0.5}
                                        readOnly
                                        size="meduim"
                                        sx={{mb: 1}}
                                    />
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