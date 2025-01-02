import BookCard from "../BookBrowsing/BookCard";
import { Grid2 } from "@mui/material";

function BookList({ books, sx }) {
  const renderedBooks = books.map(({ bookCover, bookId, bookTitle, rating, authors }) => {
    // Ensure authors is an array and use the first author, fallback to "Unknown Author"
    const author = Array.isArray(authors) && authors.length > 0 ? authors[0] : "Unknown Author";

    return (
      <Grid2 key={bookId}>
        <BookCard bookId={bookId} bookCover={bookCover} bookTitle={bookTitle} rating={rating} author={author} />
      </Grid2>
    );
  });

  return (
    <Grid2 container spacing={4} sx={sx}>
      {renderedBooks}
    </Grid2>
  );
}

export default BookList;
