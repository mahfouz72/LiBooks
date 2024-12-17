import "./BookShelves.css";
import BookShelf from "./BookShelf";
import { useNavigate } from "react-router-dom";
import { useState, useEffect, useRef } from "react";
import EditShelfModal from "../EditShelfModal/EditShelfModal";

function BookShelves() {

    const token = localStorage.getItem("token");
    
    const pageSize = 9;
    const [page, setPage] = useState(0);
    const [bookshelves, setBookshelves] = useState([]);

    const scrollContainerRef = useRef(null);
    const [operatingOnShelf, setOperatingOnShelf] = useState(null);
    
    const fetchBookshelves = async (reset = false) => {
        const currentPage = reset ? 0 : page;
        const response = await fetch(
            `http://localhost:8080/BookShelf/names?page=${currentPage}&size=${pageSize}`, {
            method: 'GET',
            headers: { "Authorization": `Bearer ${token}` },
        });
        const data = await response.json();
        if (reset) {
            setBookshelves([...data]);
            setPage(0);
        } else {
            setBookshelves((prev) => [...prev, ...data]);
        }
    };

    useEffect(() => {
        fetchBookshelves(page === 0);
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

    const renameBookshelf = async (newName, bookShelfId) => {
        const response = await fetch(
            `http://localhost:8080/BookShelf/rename/${bookShelfId}`, {
            method: 'PUT',
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"},
            body: JSON.stringify({bookShelfName: newName})
        });
        const data = await response.json();
        console.log(data);
    };

    const deleteBookshelf = async (operatingOnShelf) => {
        try {
            const response = await fetch(
                `http://localhost:8080/BookShelf/delete/${operatingOnShelf}`, {
                method: 'DELETE',
                headers: { "Authorization": `Bearer ${token}` },
            });
    
            const contentType = response.headers.get("content-type");
            let data;
            if (contentType && contentType.includes("application/json")) {
                data = await response.json();
            } else {
                data = await response.text();
            }
            console.log(data);
        } catch (error) {
            console.error("Error deleting bookshelf:", error);
        }
    }

    const onRename = async (newName) => {
        await renameBookshelf(newName, operatingOnShelf);
        await fetchBookshelves(true);
        setOperatingOnShelf(null);
    }

    const onDelete = async () => {
        await deleteBookshelf(operatingOnShelf);
        await fetchBookshelves(true);
        setOperatingOnShelf(null);
    }

    const onCancel = () => {
        setOperatingOnShelf(null);
    }

    const onRequestModal = (id) => {
        setOperatingOnShelf(id);
    }

    const navigate = useNavigate();

    const onShelfClick = (id) => {
        navigate(`/bookshelf/${id}`);
    }

    const selectedBookshelf = bookshelves.find((bookshelf) => bookshelf.bookShelfId === operatingOnShelf);

    return (
        <>
            {operatingOnShelf &&
                <EditShelfModal 
                onCancel={onCancel} 
                onRename={onRename} 
                onDelete={onDelete} 
                name={selectedBookshelf.bookShelfName}
                />
            }
            <ul className="bookShelves" ref={scrollContainerRef}>
                {bookshelves.map(({ bookShelfId, bookShelfName }) => (
                    <BookShelf
                        key={bookShelfId}
                        id={bookShelfId}
                        name={bookShelfName}
                        numberOfBooks={0}
                        onRequestModal={onRequestModal}
                        onShelfClick={onShelfClick}
                    />
                ))}
            </ul>
        </>
    );
}

export default BookShelves;