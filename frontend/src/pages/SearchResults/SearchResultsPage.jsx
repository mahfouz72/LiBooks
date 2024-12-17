import { useLocation } from "react-router-dom";
import { useState, useEffect } from "react";
import { Stack } from "@mui/material";
import Header from "../../Components/Header/Header";
import BookList from "../BookBrowsing/BookList";
import AuthorList from "./AuthorList";
import UserList from "./UserList";

function SearchResultsPage() {
    const location = useLocation();
    const [searchResults, setSearchResults] = useState([]);
    const [loading, setLoading] = useState(false);

    // Accessing results from the state passed via navigate
    const results = location.state?.results || [];
    const category = location.pathname.split("/").pop();

    useEffect(() => {
        if (results.length === 0) {
            setLoading(true);
            // In case the results are empty (which shouldn't happen if passed correctly)
            // you can fetch the data from the backend if necessary, but it's not needed 
            // if the data is passed as `results` in state.
            setLoading(false);
        } else {
            setSearchResults(results);
        }
    }, [results]);

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
