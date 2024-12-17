import { Stack } from "@mui/material";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import Header from "../../Components/Header/Header";
import BookList from "../../pages/BookBrowsing/BookList";

function BookShelfBooks() {
    const {bookShelfId} = useParams();
    const [books, setBooks] = useState([]);
    const [page, setPage] = useState(0);
    const PAGESIZE = 10;

    const token = localStorage.getItem("token");

    useEffect(() => {
        const fetchBooks = async () => {
            const response = await fetch(
                    `http://localhost:8080/BookShelf/Books?bookShelfId=${bookShelfId}&page=${page}&size=${PAGESIZE}`, {
                    method: 'GET',
                    headers: {"Authorization": `Bearer ${token}`},
                }
            );
            const data = await response.json();
            console.log(data)
            setBooks((prev) => [...prev, ...data]);
        };
        fetchBooks();
    }, [page, token]);

    const handleScroll = (e) => {
        console.log("log:paged scorlled");
        const { scrollTop, scrollHeight } = e.target.documentElement;
        const currentHeight = scrollTop + window.innerHeight;
        if (currentHeight + 1 >= scrollHeight) {
            setPage((prev) => prev + 1);
        }
    };

    useEffect(() => {
        window.addEventListener("scroll", handleScroll);
        return () => window.removeEventListener("scroll", handleScroll);
    }, []);

    return (
        <Stack spacing={4} width="100vw">
            <Header />
            <Stack direction="row" justifyContent="center" spacing={2}>
                <BookList books={books} sx={{ width: "70%" }} />
            </Stack>
        </Stack>
    );
}
export default BookShelfBooks;