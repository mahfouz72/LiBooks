

function BookShelfItem({ key, name, numberOfBooks }) {
    return (
        <div className="bookShelfItem">
            <h3>{name}</h3>
            <p>{numberOfBooks} books</p>
        </div>
    );
}

export default BookShelfItem;