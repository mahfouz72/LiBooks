import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {Box, Button, Divider, Grid, Stack} from "@mui/material";
import BookCover from "./BookCover";
import BookDetails from "./BookDetails";
import BookAdditionalInfo from "./BookAdditionalInfo";
import Header from "../../Components/Header/Header";
import ReviewList from "../BookBrowsing/ReviewList";
import ReviewForm  from "../BookBrowsing/ReviewForm";
import AddToShelvesModal from "../../Components/AddToShelvesModal/AddToShelvesModal";
import ShowOffersButton from "../BookOffers/ShowOffersButton";

function BookDetailsPage() {
    const {bookId} = useParams();
    const [book, setBook] = useState([]);
    const [reviews, setReviews] = useState([]);
    const [dialogOpen, setDialogOpen] = useState(false);
    const [addToShelvesModalOpen, setAddToShelvesModalOpen] = useState(false);
    const token = localStorage.getItem("token");

    const fetchBook = async () => {
        try {
            const response = await fetch(
                `http://localhost:8080/books/${bookId}`,
                {
                    method: 'GET',
                    headers: {"Authorization": `Bearer ${token}`},
                }
            );
            if (!response.ok) {
                throw new Error("Failed to fetch book details");
            }
            const data = await response.json();
            setBook(data);
        } catch (error) {
            console.error("Error fetching book details:", error);
        }
    };

    useEffect(() => {
        fetchBook().then(r => console.log(book));
    }, [bookId, token]);


    const fetchReviews = async () => {
        try {
            const response = await fetch(`http://localhost:8080/reviews/${bookId}`, {
                method: 'GET',
                headers: {"Authorization": `Bearer ${token}`},
            });
            if (!response.ok) {
                throw new Error("Failed to fetch reviews");
            }
            const data = await response.json();
            setReviews(data);
        } catch (error) {
            console.error("Error fetching reviews:", error);
        }
    };

    useEffect(() => {
        fetchReviews().then(r => console.log(reviews));
    }, [bookId, token]);

    const handleDialogOpen = () => {
        setDialogOpen(true);
    };

    const handleDialogClose = () => {
        setDialogOpen(false);
    };

    const handleReviewSubmit = async (newReview) => {
        try {
            const response = await fetch(`http://localhost:8080/reviews/add?bookId=${bookId}`, {
                method: 'POST',
                headers: {
                    "Authorization": `Bearer ${token}`,
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({...newReview, bookId})
            });
            if (!response.ok) {
                throw new Error("Failed to submit review");
            }
            const newReviewData = await response.json();
            setReviews([...reviews, newReviewData]);

            fetchBook();
            
            handleDialogClose();
        } catch (error) {
            console.error("Error submitting review:", error);
        }
    };


    return (
        <Stack spacing={4} width="100vw" justifyContent="center" alignItems="center">
            <Header/>
            <Box sx={{p: 4}}>
                <Grid container spacing={4}>
                    <Grid item xs={12} md={4}>
                        <BookCover bookCover={book.bookCover} bookTitle={book.bookTitle}/>
                    </Grid>
                    <Grid item xs={12} md={8}>
                        <BookDetails book={book}/>
                        <Divider sx={{my: 2}}/>
                        <BookAdditionalInfo book={book}/>
                        <ShowOffersButton bookId={bookId}/>
                    </Grid>
                </Grid>
                <Button 
                    variant="contained"
                    sx={{
                        width: '38vh',
                        textTransform: 'none',
                        backgroundColor: 'black',
                        color: 'white',
                    }}
                    onClick = {() => setAddToShelvesModalOpen(true)}
                >
                    Add to BookShelf +
                </Button>
                {addToShelvesModalOpen && (
                    <AddToShelvesModal
                        bookId={bookId}
                        onClose={() => setAddToShelvesModalOpen(false)}
                    />
                )}
                <Divider sx={{my: 4}}/>
                <Button variant="contained" onClick={handleDialogOpen}
                        sx={{
                            width: 'auto',
                            maxWidth: 200,
                            textTransform: 'none',
                            backgroundColor: 'black',
                            color: 'white',
                        }}>
                    Write a Review
                </Button>
                <ReviewList reviews={reviews}/>
                {dialogOpen && (
                    <ReviewForm onSubmit={handleReviewSubmit} onClose={handleDialogClose}/>
                )}
            </Box>
        </Stack>
    );
}

export default BookDetailsPage;