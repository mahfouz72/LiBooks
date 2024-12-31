import { Box, Grid2, Typography } from "@mui/material";
import ActivityCard from "./ActivityCard";
import { useEffect, useState } from "react";

const reviews = [
    {
        username: "Esmail",
        reviewText: "This book was amazing! Highly recommend.This book was amazing! Highly recommend.This book was amazing! " +
                    "Highly recommend.This book was amazing! Highly recommend.This book was amazing! Highly recommend.T" +
                    "his book was amazing! Highly recommend.This book was amazing! Highly recommend.",
        rating: 4.5,
        date: "2024-12-31T17:00:00.000Z"
    },
    {
        username: "John",
        reviewText: "Great read! The plot was engaging and kept me hooked until the very end. Will definitely check out more books by this author.",
        rating: 5,
        date: "2024-12-30T14:30:00.000Z"
    },
    {
        username: "Sara",
        reviewText: "It was a good book, but the pacing felt a bit slow in parts. The characters were well-developed, though.",
        rating: 3.8,
        date: "2024-12-29T10:15:00.000Z"
    },
    {
        username: "Michael",
        reviewText: "A thrilling story from start to finish. I couldn't put the book down! Highly recommended for fans of suspense.",
        rating: 4.9,
        date: "2024-12-28T08:45:00.000Z"
    },
    {
        username: "Sophia",
        reviewText: "I enjoyed this book, but it didn't live up to my expectations. Some parts were predictable, but the writing was still good.",
        rating: 3.2,
        date: "2024-12-27T20:00:00.000Z"
    }
];

function ActivityList() {
    // const [reviews, setReviews] = useState([]);

    const token = localStorage.getItem("token");

    // useEffect(() => {
    //     const getRecentActivity = async () => {
    //         const response = await fetch(
    //                 `http://localhost:8080/activity`, {
    //                 method: 'GET',
    //                 headers: {"Authorization": `Bearer ${token}`},
    //             }
    //         );
    //         const data = await response.json();
    //         console.log(data)
    //         setReviews(data);
    //     };
    //     getRecentActivity();
    // }, [token]);

    return (
        <Box>
            <Typography ml={4} variant="h5" color="error" fontWeight="bold" fontStyle="italic">
                Recent Activity
            </Typography>
            <Grid2 container spacing={2}>
                {
                    reviews.map((review,i) => {
                        return (
                            <Grid2 key={i}>
                                <ActivityCard  review={review} bookName="The Hobbit" />
                            </Grid2>
                        )
                    })
                }
            </Grid2>
        </Box>
    )
}
export default ActivityList;