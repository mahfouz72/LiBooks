import { Avatar, Box, Rating, Stack, Typography } from "@mui/material";

function ActivityCard({bookName,review}) {
    const {username, reviewText, rating, date} = review;

    const givenDate = new Date(date); 
    const now = new Date();
    now.setHours(now.getHours() + 2);

    console.log("Given Date:", givenDate.toISOString());
    console.log("Now:", now.toISOString());
    const diffMs = now - givenDate;
    
    const diffMins = Math.floor(diffMs / (1000 * 60));
    const diffHours = Math.floor(diffMins / 60);
    const diffDays = Math.floor(diffHours / 24);
    const diffWeeks = Math.floor(diffDays / 7);

    let time;
    if (diffMins < 60) 
        time = `${diffMins} miuntes ago`;
    else if (diffHours < 24)
        time = `${diffHours} hours ago`;
    else if (diffDays < 7)
        time = `${diffDays} days ago`;
    else
        time = `${diffWeeks} weeks ago`;

    console.log(time);
    return (
        <Box width="50%" minHeight="200px" p={4} border="2px solid #974903" borderRadius="30px" bgcolor="aliceblue">
            <Box height="100%" width="100%">
                <Stack direction="row" alignItems="center" spacing={2}>
                    <Avatar>{username[0]}</Avatar>
                    <Typography variant="h5" fontWeight="bold" >
                        {username}
                    </Typography>
                    <Typography variant="h5">
                        reviewed the book
                    </Typography>
                    <Typography variant="h5" fontWeight="bold">
                        {bookName}
                    </Typography>
                </Stack>
                <Stack ml={7}>
                    <Typography ml={1} variant="subtitle1" fontWeight="bold">
                        {time}
                    </Typography>
                    <Rating  name="review-rating" value={rating} precision={0.5} readOnly/>
                    <Typography variant="body1">
                        {reviewText}
                    </Typography>
                </Stack>
            </Box>
        </Box>
    )
}
export default ActivityCard;