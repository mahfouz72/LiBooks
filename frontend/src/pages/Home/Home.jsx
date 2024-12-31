import { Box, Stack} from "@mui/material";
import Header from "../../Components/Header/Header";
import Footer from "../../Components/Footer/footer";
import BookSlider from "../../Components/Silder/BookSlider";
import { useEffect, useState } from "react";
import ActivityList from "./ActivityList";

function Home() {
    const [latestBooks, setLatestBooks] = useState([]);
    const [recommenedBooks, setRecommenedBooks] = useState([]);
    const [isloading, setIsloading] = useState(true);
    const token = localStorage.getItem("token");

    useEffect(() => {
            const fetchLatestBooks = async () => {
                const response = await fetch(
                        `http://localhost:8080/books/latest`, {
                        method: 'GET',
                        headers: {"Authorization": `Bearer ${token}`},
                    }
                );
                const data = await response.json();
                setIsloading(false);
                console.log(data)
                setLatestBooks(data);
            };
            fetchLatestBooks();
    }, [token]);

    useEffect(() => {
        const fetchRecommenedBooks = async () => {
            const response = await fetch(
                    `http://localhost:8080/recommendations/books`, {
                    method: 'GET',
                    headers: {"Authorization": `Bearer ${token}`},
                }
            );
            const data = await response.json();
            setIsloading(false);
            console.log(data)
            setRecommenedBooks(data);
        };
        fetchRecommenedBooks();
    }, [token]);

    return (
    <Box width="100vw" height="100%">
        <Header />
        <Stack mt={5} width="80%" mx="auto" spacing={5}>
            <BookSlider isloading={isloading} books={latestBooks} title="Latest Books" linkTitle="All Books" link="/BookBrowsingPage"/>
            <BookSlider isloading={isloading} books={recommenedBooks} title="Recommend Books" linkTitle="See more" link="/BookBrowsingPage"/>
            {/* Recent Activity */}
            <ActivityList/>
        </Stack>
        <Footer/>
    </Box>
  );
}

export default Home;
