import BookShelf from "./BookShelf";
import "./BookShelves.css";

function BookShelves() {

    const bookshelves = [
        {id: 1, name: "Currently Reading", numberOfBooks: 2},
        {id: 2, name: "Want to Read", numberOfBooks: 1},
        {id: 3, name: "Read", numberOfBooks: 3},
        {id: 4, name: "Favourites", numberOfBooks: 5},
        {id: 5, name: "All", numberOfBooks: 11},
        {id: 6, name: "To Review", numberOfBooks: 0},
        {id: 7, name: "Reviewed", numberOfBooks: 0},
        {id: 8, name: "Wishlist", numberOfBooks: 0},
        {id: 9, name: "Currently Reading", numberOfBooks: 2},
        {id: 10, name: "Want to Read", numberOfBooks: 1},
        {id: 11, name: "Read", numberOfBooks: 3},
        {id: 12, name: "Favourites", numberOfBooks: 5},
        {id: 13, name: "All", numberOfBooks: 11},
        {id: 14, name: "To Review", numberOfBooks: 0},
        {id: 15, name: "Reviewed", numberOfBooks: 0},
        {id: 16, name: "Wishlist", numberOfBooks: 0},
        {id: 17, name: "Currently Reading", numberOfBooks: 2},
    ];

    const pageSize = 8;

    return (
        <ul className="bookShelves">
            {bookshelves.map((bookshelf) => {
                return (
                    <BookShelf key={bookshelf.id} name={bookshelf.name} numberOfBooks={bookshelf.numberOfBooks} />
                );
            })}
        </ul>
    );
}

export default BookShelves;