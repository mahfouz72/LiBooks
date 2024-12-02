import {Stack} from "@mui/material";
import Header from "../Common/Header";
import BookList from "./BookList";
import bookCover from "../assests/book cover.jpg";
import bookCover1 from "../assests/book cover1.jpg";
import bookCover2 from "../assests/book cover2.jpg";
const books = [
    {
        bookCover: bookCover,
        bookId: 1,
        bookTitle: "Book",
        author: "Agatha",
        rating: 2.5
    },
    {
        bookCover: bookCover1,
        BookId: 2,
        bookTitle: "And Then There Were none",
        author: "Agatha1",
        rating: 3
    },
    {
        bookCover: bookCover2,
        bookId: 3,
        bookTitle: "Book2",
        author: "Agatha2",
        rating: 5
    },
    {
        bookCover: bookCover,
        bookId: 4,
        bookTitle: "Book",
        author: "Agatha",
        rating: 1
    },
    {
        bookCover: bookCover1,
        bookId: 5,
        bookTitle: "Book1",
        author: "Agatha1",
        rating: 3
    },
    {
        bookCover: bookCover2,
        bookId: 6,
        bookTitle: "Book2",
        author: "Agatha2",
        rating: 5
    },
    {
        bookCover: bookCover,
        bookId: 7,
        bookTitle: "Book",
        author: "Agatha",
        rating: 1
    },
    {
        bookCover: bookCover1,
        bookId: 8,
        bookTitle: "Book1",
        author: "Agatha1",
        rating: 3
    },
    {
        bookCover: bookCover2,
        bookId: 9,
        bookTitle: "Book2",
        author: "Agatha2",
        rating: 5
    },
    {
        bookCover: bookCover,
        bookId: 10,
        bookTitle: "Book",
        author: "Agatha",
        rating: 1
    }
]

function BookBrowsingPage(){
    return(
         <Stack spacing={4}>
            <Header/>
            <Stack direction="row" justifyContent="center" spacing={2}  >
                <BookList books={books} sx={{width:'80%'}}/>
            </Stack>
        </Stack>
    )
}
export default BookBrowsingPage;