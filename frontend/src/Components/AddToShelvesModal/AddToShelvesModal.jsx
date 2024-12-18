import "./AddToShelvesModal.css";
import {Button} from "@mui/material";
import { useState, useEffect, useRef } from "react";

function AddToShelvesModal({bookId, onClose}) {

    const token = localStorage.getItem("token");
    
    const pageSize = 7;
    const [page, setPage] = useState(0);
    const [bookShelves, setBookShelves] = useState([]);

    const scrollContainerRef = useRef(null);

    const fetchBookShelves = async () => {
        const response = await fetch(
            `http://localhost:8080/BookShelf/names?page=${page}&size=${pageSize}`, {
            method: 'GET',
            headers: { "Authorization": `Bearer ${token}` },
        });
        const data = await response.json();
        setBookShelves((prev) => [...prev, ...data]);
    }

    useEffect(() => {
        fetchBookShelves();
    }, [page]);

    const handleScroll = (e) => {
        const { scrollTop, scrollHeight, clientHeight } = e.target;
        if (scrollTop + clientHeight + 2 >= scrollHeight) {
            setPage((prev) => prev + 1);
        }
    }

    useEffect(() => {
        const scrollContainer = scrollContainerRef.current;
        if (scrollContainer) {
            scrollContainer.addEventListener('scroll', handleScroll);
        }
        return () => {
            if (scrollContainer) {
                scrollContainer.removeEventListener('scroll', handleScroll);
            }
        };
    }, []);

    const addBookToShelf = async (bookShelfId) => {
        const response = await fetch(
            `http://localhost:8080/BookShelf/Books/add`, {
            method: 'POST',
            headers: { 
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                bookId: bookId,
                bookShelfId: bookShelfId
            })
        });
        onClose();
    }

    const createBookShelf = async () => {
        const response = await fetch(
            `http://localhost:8080/BookShelf/add`, {
            method: 'POST',
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                bookShelfName: "New BookShelf"
            })
        });
        const data = await response.json();
        setBookShelves((prev) => [...prev, data]);
    }

    const handleClose = (close, e) => {
        if (close) {
            onClose();
        }else{
            e.stopPropagation();
        }
    }

    return (
        <div className="addToShelfModalWrapper" onClick={(e) => handleClose(true, e)}>
            <div className="addToShelfModal" onClick={(e) => handleClose(false, e)}>
                <ul
                    className="addToBookShelvesList"
                    ref={scrollContainerRef}
                >
                    {
                        bookShelves.map(({ bookShelfId, bookShelfName }) => (
                            <li key={bookShelfId} className="addToBookShelfItem" onClick={() => addBookToShelf(bookShelfId)}>
                                    {bookShelfName}
                            </li>
                        ))
                    }
                </ul>
                <Button
                    variant="contained"
                    sx = {{backgroundColor: "grey", color: "white"}}
                    onClick = {createBookShelf}
                >
                    Create BookShelf
                </Button>
            </div>
        </div>
    )
}

export default AddToShelvesModal;