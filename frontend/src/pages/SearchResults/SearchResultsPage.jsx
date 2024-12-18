import { useLocation } from "react-router-dom";
import { useState, useEffect } from "react";
import { Stack } from "@mui/material";
import Header from "../../Components/Header/Header";
import BookList from "./BookList";
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
            setSearchResults([]);
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

    const renderMessage = () => {
        if (searchResults.length === 0) {
            return (
                <Stack padding="100px" justifyContent="center">
                    <h1 style={{ fontSize: '100px' }}>:(</h1>
                    <h2 style={{ whiteSpace: 'nowrap' }}>Sorry, no {category} matched your search.</h2>
                </Stack>
            );
        }
        return null;
    };

    return (
        <Stack spacing={4} width="100vw">
            <Header />
            <Stack direction="row" justifyContent="center" spacing={2}>
                {renderMessage()}
                {category === "Books" && <BookList books={searchResults} sx={{ width: "70%" }} />}
                {category === "Authors" && <AuthorList authors={searchResults} sx={{ width: "70%" }} />}
                {category === "Users" && <UserList users={searchResults} sx={{ width: "70%" }} />}
            </Stack>
        </Stack>
    );
}

export default SearchResultsPage;
