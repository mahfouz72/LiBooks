import BookCard from "./BookCard";
import {Stack} from "@mui/material";

function BookList({books}){
    const renderedBooks = books.map(({bookCover,title,author,rating})=>{
        return <BookCard bookCover={bookCover} title={title} author={author} rating={rating}/>
    })
    return(
        <Stack direction="row" spacing={2} display="flex" justifyContent="space-between">
            {renderedBooks}
        </Stack>
    )
}
export default BookList;