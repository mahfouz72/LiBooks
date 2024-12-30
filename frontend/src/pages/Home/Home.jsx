import { Box, Stack} from "@mui/material";
import Header from "../../Components/Header/Header";
import BookSlider from "../../Components/Silder/BookSlider";
import { useEffect, useState } from "react";

function Home() {
    const [books, setBooks] = useState([]);
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
                setBooks(data);
            };
            fetchLatestBooks();
    }, [token]);

    return (
    <Box width="100vw" height="100%">
        <Header />
        <Stack mt={5} width="80%" mx="auto" spacing={5}>
            <BookSlider isloading={isloading} books={books} title="Latest Books" linkTitle="All Books" link="/BookBrowsingPage"/>
            <BookSlider isloading={isloading} books={books} title="Recommend Books" linkTitle="See more" link="/BookBrowsingPage"/>
            {/* Recent Activity */}
      </Stack>
    </Box>
  );
}

export default Home;
