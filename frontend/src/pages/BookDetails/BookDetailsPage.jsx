import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {Box, Divider, Grid, Stack} from "@mui/material";
import BookCover from "./BookCover";
import BookDetails from "./BookDetails";
import BookAdditionalInfo from "./BookAdditionalInfo";
import Header from "../../Components/Header/Header";


function BookDetailsPage() {
    const {bookId} = useParams();
    const [book, setBook] = useState([]);
    const token = localStorage.getItem("token");

    useEffect(() => {
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
                setBook(data); // Set the fetched book data
            } catch (error) {
                console.error("Error fetching book details:", error);
            }
        };

        fetchBook().then(r => console.log(book));
    }, [bookId, token]);

    return (
        <Stack spacing={4} width="100vw">
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
                    </Grid>
                </Grid>
            </Box>
        </Stack>
    );
}

export default BookDetailsPage;