import { Stack } from "@mui/material";
import Header from "../../Components/Header/Header";
import BookList from "../BookBrowsing/BookList";
import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";

function SearchResultsPage() {
    const [searchResults, setSearchResults] = useState([]);
    const [loading, setLoading] = useState(true);
    const location = useLocation();
    const params = new URLSearchParams(location.search);
    const query = params.get("query");
    const category = location.pathname.split("/").pop();
    const token = localStorage.getItem("token");

    useEffect(() => {
        const fetchSearchResults = async () => {
            try {
                setLoading(true);
                const response = await fetch(
                    `http://localhost:8080/search/${category}?query=${encodeURIComponent(query)}`,
                    {
                        method: "GET",
                        headers: { Authorization: `Bearer ${token}` },
                    }
                );
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                const data = await response.json();
                setSearchResults(data);
            } catch (error) {
                console.error("Error fetching search results:", error);
            } finally {
                setLoading(false);
            }
        };

        fetchSearchResults();
    }, [query, category, token]);

    if (loading) {
        return (
            <Stack alignItems="center" justifyContent="center" height="100vh">
                <h2>Loading...</h2>
            </Stack>
        );
    }

    return (
        <Stack spacing={4} width="100vw">
            <Header />
            <Stack direction="row" justifyContent="center" spacing={2}>
                {category === "Books" && <BookList books={searchResults} sx={{ width: "70%" }} />}
                {category === "Authors" && <AuthorList authors={searchResults} sx={{ width: "70%" }} />}
                {category === "Users" && <UserList users={searchResults} sx={{ width: "70%" }} />}
            </Stack>
        </Stack>
    );
}

export default SearchResultsPage;