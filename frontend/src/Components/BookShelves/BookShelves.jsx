import BookShelf from "./BookShelf";

function BookShelves() {

    const bookshelves = [
        {
            name: "Currently Reading",
            numberOfBooks: 2
        },
        {
            name: "Want to Read",
            numberOfBooks: 1
        },
        {
            name: "Read",
            numberOfBooks: 3
        },
        {
            name: "Favourites",
            numberOfBooks: 5
        },
        {
            name: "All",
            numberOfBooks: 11
        }
    ];

    return (
        <ul>
            {bookshelves.map((bookshelf, index) => {
                return (
                    <BookShelf key={index} name={bookshelf.name} numberOfBooks={bookshelf.numberOfBooks} />
                );
            })}
        </ul>
    );
}

export default BookShelves;