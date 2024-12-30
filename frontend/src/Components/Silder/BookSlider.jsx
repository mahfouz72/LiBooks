import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import { Stack, Typography } from "@mui/material";
import { Link } from "react-router-dom";
import BookCard from "../../pages/BookBrowsing/BookCard";
import "./BookSlider.css"
import LoadingBookCard from "../../pages/BookBrowsing/LoadingBookCard";

const settings = {
    dots: false,
    infinite: true,
    slidesToShow: 6,
    slidesToScroll: 1,
    speed: 500,
};

function BookSlider({books, title, linkTitle, link , isloading}) {
    const renderedBooks = books.map(
        ({ bookCover, bookId, bookTitle, rating, authors }) => {
            return (
            <BookCard
                key={bookId}
                bookId={bookId}
                bookCover={bookCover}
                bookTitle={bookTitle}
                rating={rating}
                author={authors[0]}
                isloading = {isloading}
              />
            );
        }
    );
    
    const loadingBooks = Array(6).fill(null).map((_, index) => (
        <LoadingBookCard key={index} />
      ));
    
    return(
        <Stack>
            <Typography ml={4} variant="h5" color="error" fontWeight="bold" fontStyle="italic">
                {title}
            </Typography>
            <Stack border="5px solid #974903" borderRadius="30px" bgcolor="aliceblue">
                <Stack alignItems="flex-end">
                    <Link to={link} style={{textDecorationColor: '#1976d2'}}>
                        <Typography mr={2} color="primary"  variant="body1" fontWeight="bold">
                            {linkTitle}
                        </Typography>
                    </Link>
                </Stack>
                <Slider {...settings} className="custom-slider">
                    {isloading ? loadingBooks :renderedBooks}
                </Slider>        
            </Stack>
        </Stack>
    );
}
export default BookSlider;