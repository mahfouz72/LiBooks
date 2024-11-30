import {Stack} from "@mui/material";
import Header from "../Common/Header";
import BookList from "./BookList";
import bookCover from "../assests/book cover.jpg";
import bookCover1 from "../assests/book cover1.jpg";
import bookCover2 from "../assests/book cover2.jpg";
const books = [
    {
        bookCover: bookCover,
        title: "Book",
        author: "Agatha",
        rating: 1
    },
    {
        bookCover: bookCover1,
        title: "Book1",
        author: "Agatha1",
        rating: 3
    },
    {
        bookCover: bookCover2,
        title: "Book2",
        author: "Agatha2",
        rating: 5
    },
    {
        bookCover: bookCover,
        title: "Book",
        author: "Agatha",
        rating: 1
    },
    {
        bookCover: bookCover1,
        title: "Book1",
        author: "Agatha1",
        rating: 3
    },
    {
        bookCover: bookCover2,
        title: "Book2",
        author: "Agatha2",
        rating: 5
    }
]

function BookBrowsingPage(){
    return(
        <Stack spacing={4} >
            <Header/>
            <BookList books={books}/>
        </Stack>
    )
}
export default BookBrowsingPage;