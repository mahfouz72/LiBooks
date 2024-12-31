import { Box, Typography } from "@mui/material";
import ActivityCard from "./ActivityCard";

const review = {
    username: "Esmail",
    reviewText: 
    "This book was amazing! Highly recommend.This book was amazing! Highly recommend.This book was amazing! "+
    "Highly recommend.This book was amazing! Highly recommend.This book was amazing! Highly recommend.T"+
    " his book was amazing! Highly recommend.This book was amazing! Highly recommend.",
    rating: 4.5,
    date: "2024-12-31T17:00:00.000Z"
};

function ActivityList() {
    return (
        <Box>
            <Typography ml={4} variant="h5" color="error" fontWeight="bold" fontStyle="italic">
                Recent Activity
            </Typography>
            <ActivityCard review={review} bookName="The Hobbit" />
        </Box>
    )
}
export default ActivityList;