import BookCard from "./BookCard";
import {Grid2} from "@mui/material";

function BookList({books,sx}){
    const renderedBooks = books.map(({bookCover,bookId,bookTitle,rating,authors})=>{
        return(
            <Grid2 key={bookId}>
                <BookCard bookId={bookId} bookCover={bookCover} bookTitle={bookTitle} rating={rating} author={authors[0]}/>
            </Grid2>
        )
    })
    return(
        <Grid2 container spacing={4} sx={sx}>
            {renderedBooks}
        </Grid2>
    )
}
export default BookList;