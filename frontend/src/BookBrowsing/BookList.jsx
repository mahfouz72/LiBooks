import BookCard from "./BookCard";
import {Grid2} from "@mui/material";

function BookList({books,sx}){
    const renderedBooks = books.map(({bookCover,title,author,rating})=>{
        return(
            <Grid2>
                <BookCard bookCover={bookCover} title={title} author={author} rating={rating}/>
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