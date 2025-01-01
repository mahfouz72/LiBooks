import React, { useState, useEffect } from "react";
import axios from "axios";

function AdminDashboard() {
  // State for managing data
  const [books, setBooks] = useState([]);
  const [authors, setAuthors] = useState([]);
  const [users, setUsers] = useState([]);
  const [totalBooks, setTotalBooks] = useState(0);
  const [totalAuthors, setTotalAuthors] = useState(0);
  const [totalUsers, setTotalUsers] = useState(0);
  const [currentPage, setCurrentPage] = useState(1);
  const [currentSize, setCurrentSize] = useState(5);
  const [currentPage2, setCurrentPage2] = useState(1);
  const [currentSize2, setCurrentSize2] = useState(5);
  const [isbnToDelete, setIsbnToDelete] = useState("");


  // State for form inputs
  const [bookForm, setBookForm] = useState({
    bookTitle: "",
    bookCover: null,
    isbn: "",
    genre: "",
    languageOfOrigin: "",
    publicationDate: "",
    summary: "",
    authors: [],
  });

  const [authorForm, setAuthorForm] = useState({
    authorName: "",
    authorPhoto: null,
    authorBiography: "",
    authorBirthDate: "",
    nationality: "",
  });
  


  const fetchData = async (url, setter, method = "GET", data = null) => {
    try {
        let response;
        if (method === "POST") {
            response = await axios.post(url, data);
        } 
        else if (method === "PUT") {
            response = await axios.put(url);
        }
        else if (method === "DELETE") {
            response = await axios.delete(url);
        }
        else {
            response = await axios.get(url);
        } 
        setter(response.data);
        console.log(response.data);
    } catch (error) {
        console.error("Error fetching data:", error);
    }
};

useEffect(() => {
    fetchData('http://localhost:8080/api/authors/count', setTotalAuthors, 'GET');
    fetchData('http://localhost:8080/books/count', setTotalBooks, 'GET');
    fetchData('http://localhost:8080/users/count', setTotalUsers, 'GET');
}, []);

useEffect(() => {
    fetchData(`http://localhost:8080/api/authors/all?page=${currentPage-1}&size=${currentSize}`, setAuthors, 'POST');

}, [currentPage]);

useEffect(() => {
    fetchData(`http://localhost:8080/users/all?page=${currentPage-1}&size=${currentSize}`, setUsers, 'POST', { page: currentPage-1, size: currentSize });

}, [totalUsers]);

  // Handlers for adding a new book
  const handleAddBook = async (e) => {
    e.preventDefault();
    console.log(bookForm);
    try {
        const response = await axios.post('http://localhost:8080/books/add-book', bookForm);
        if (response.status === 200) {
            setTotalBooks(totalBooks + 1);
            setBookForm({
                bookTitle: "",
                bookCover: null,
                isbn: "",
                genre: "",
                languageOfOrigin: "",
                publicationDate: "",
                summary: "",
                authors: [],
              });
        }
    } catch (error) {
        console.error("Error adding book:", error);
    }
    
  };

  // Handlers for adding a new author
  const handleAddAuthor = async (e) => {
    e.preventDefault();
    console.log(authorForm);
    try {
        const response = await axios.post('http://localhost:8080/api/authors/add-author', authorForm);
        if (response.status === 200) {
            setTotalAuthors(totalAuthors + 1);
            setAuthorForm({ authorName: "", authorPhoto: null, authorBiography: "", authorBirthDate: "", nationality: "" });
        }
    } catch (error) {
        console.error("Error deleting book:", error);
    }
  };

  // Handle image preview for books and authors
  const handleImageUpload = (e, field) => {
    const file = e.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => {
        const byteArray = new Uint8Array(reader.result);
        if (field === "cover") {
          setBookForm({ ...bookForm, cover: byteArray });
        } else if (field === "photo") {
          setAuthorForm({ ...authorForm, authorPhoto: byteArray });
        }
      };
      reader.readAsArrayBuffer(file);
    }
  };


  // Delete book by ISBN
    const handleDeleteBook = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.delete(`http://localhost:8080/books/delete-by-isbn?isbn=${isbnToDelete}`);
            if (response.status === 200) {
                setTotalBooks(totalBooks - 1);
                setIsbnToDelete("");
            }
        } catch (error) {
            console.error("Error deleting book:", error);
        }
    };

    const deleteUser = async (id) => {
        if (window.confirm("Are you sure you want to delete this user?")) {
            try {
                const response = await axios.delete(`http://localhost:8080/users/${id}`);
                if (response.status === 200) {
                    setTotalUsers(totalUsers - 1);
                }
            } catch (error) {
                console.error("Error deleting book:", error);
            }
        }
    };

  return (
    <div style={containerStyle}>
      <h1 style={headerStyle}>Admin Dashboard</h1>

      {/* Dashboard Stats */}
      <div style={statsStyle}>
        <div style={statCardStyle}>
          Number of Books: <strong>{totalBooks}</strong>
        </div>
        <div style={statCardStyle}>
          Number of Authors: <strong>{totalAuthors}</strong>
        </div>
        <div style={statCardStyle}>
          Number of Users: <strong>{totalUsers}</strong>
        </div>
      </div>

      {/* Add New Book Form */}
    <h2 style={formTitleStyle}>Add New Book</h2>
    <form onSubmit={handleAddBook} style={formStyle}>
    <input
        type="text"
        placeholder="Title"
        value={bookForm.bookTitle}
        onChange={(e) => setBookForm({ ...bookForm, bookTitle: e.target.value })}
        required
        style={inputStyle}
    />
    <input
        type="file"
        onChange={(e) => handleImageUpload(e, "cover")}
        accept="image/*"
        style={inputStyle}
    />
    {bookForm.bookCover && <img src={bookForm.bookCover} alt="Book Cover" style={imagePreviewStyle} />}
    <input
        type="text"
        placeholder="ISBN"
        value={bookForm.isbn}
        onChange={(e) => setBookForm({ ...bookForm, isbn: e.target.value })}
        style={inputStyle}
    />
    <input
        type="text"
        placeholder="Genre"
        value={bookForm.genre}
        onChange={(e) => setBookForm({ ...bookForm, genre: e.target.value })}
        style={inputStyle}
    />
    <input
        type="text"
        placeholder="Language"
        value={bookForm.languageOfOrigin}
        onChange={(e) => setBookForm({ ...bookForm, languageOfOrigin: e.target.value })}
        style={inputStyle}
    />
    <input
        type="date"
        value={bookForm.publicationDate}
        onChange={(e) => setBookForm({ ...bookForm, publicationDate: e.target.value })}
        style={inputStyle}
    />
    <textarea
        placeholder="Summary"
        value={bookForm.summary}
        onChange={(e) => setBookForm({ ...bookForm, summary: e.target.value })}
        style={textareaStyle}
    ></textarea>

  {/* Authors Input */}
    <div>
    <h3>Authors</h3>
    {bookForm.authors.map((author, index) => (
      <div key={index} style={{ display: "flex", alignItems: "center", marginBottom: "5px" }}>
        <input
          type="text"
          placeholder={`Author ${index + 1}`}
          value={author}
          onChange={(e) => {
            const updatedAuthors = [...bookForm.authors];
            updatedAuthors[index] = e.target.value;
            setBookForm({ ...bookForm, authors: updatedAuthors });
          }}
          style={inputStyle}
        />
        <button
          type="button"
          onClick={() => {
            const updatedAuthors = bookForm.authors.filter((_, i) => i !== index);
            setBookForm({ ...bookForm, authors: updatedAuthors });
          }}
          style={buttonStyle}
        >
          Remove
        </button>
      </div>
    ))}
    <button
      type="button"
      onClick={() =>
        setBookForm({ ...bookForm, authors: [...bookForm.authors, ""] })
      }
      style={buttonStyle}
    >
      Add Author
    </button>
    </div>
    <button type="submit" style={buttonStyle}>Add Book</button>
    </form>
      
      {/* Delete Book Form */}
      <h2 style={formTitleStyle}>Delete Book by ISBN</h2>
      <form onSubmit={handleDeleteBook} style={formStyle}>
        <input
          type="text"
          placeholder="Enter ISBN to Delete"
          value={isbnToDelete}
          onChange={(e) => setIsbnToDelete(e.target.value)}
          style={inputStyle}
        />
        <button type="submit" style={deleteButtonStyle}>Delete Book</button>
      </form>

      {/* Add New Author Form */}
      <h2 style={formTitleStyle}>Add New Author</h2>
      <form onSubmit={handleAddAuthor} style={formStyle}>
        <input
          type="text"
          placeholder="Name"
          value={authorForm.authorName}
          onChange={(e) => setAuthorForm({ ...authorForm, authorName: e.target.value })}
          required
          style={inputStyle}
        />
        <input
          type="file"
          onChange={(e) => handleImageUpload(e, "photo")}
          accept="image/*"
          style={inputStyle}
        />
        {authorForm.authorPhoto && <img src={authorForm.authorPhoto} alt="Author" style={imagePreviewStyle} />}
        <textarea
          placeholder="Biography"
          value={authorForm.authorBiography}
          onChange={(e) => setAuthorForm({ ...authorForm, authorBiography: e.target.value })}
          style={textareaStyle}
        ></textarea>
        <input
          type="date"
          value={authorForm.authorBirthDate}
          onChange={(e) => setAuthorForm({ ...authorForm, authorBirthDate: e.target.value })}
          style={inputStyle}
        />
        <input
          type="text"
          placeholder="Nationality"
          value={authorForm.nationality}
          onChange={(e) => setAuthorForm({ ...authorForm, nationality: e.target.value })}
          style={inputStyle}
        />
        <button type="submit" style={buttonStyle}>Add Author</button>
      </form>

      {/* Authors Table */}
      <h2 style={tableTitleStyle}>All Authors</h2>
      <table style={tableStyle}>
        <thead>
          <tr style={tableHeaderStyle}>
            <th>Name</th>
            <th>Photo</th>
            <th>Biography</th>
            <th>Date of Birth</th>
            <th>Nationality</th>
          </tr>
        </thead>
        <tbody>
          {authors.map((author, index) => (
            <tr key={index} style={index % 2 === 0 ? rowStyleEven : rowStyleOdd}>
              <td>{author.authorName}</td>
              <td><img src={author.photo} alt={author.name} style={imageCellStyle} /></td>
              <td>{author.biography}</td>
              <td>{author.dob}</td>
              <td>{author.nationality}</td>
            </tr>
          ))}
        </tbody>
      </table>
        {/* Pagination Controls */}
        <div style={paginationStyle}>
        <button
            disabled={currentPage === 1}
            onClick={() => setCurrentPage((prev) => Math.max(prev - 1, 1))}
            style={paginationButtonStyle}
        >
            Previous
        </button>
        <span>Page {currentPage} of {Math.ceil(totalAuthors / currentSize)}</span>
        <button
            disabled={currentPage === Math.ceil(totalAuthors / currentSize)}
            onClick={() => setCurrentPage((prev) => Math.min(prev + 1, Math.ceil(totalAuthors / currentSize)))}
            style={paginationButtonStyle}
        >
            Next
        </button>
        </div>

      {/* Users Table */}
        <h2 style={tableTitleStyle}>All Users</h2>
        <table style={tableStyle}>
        <thead>
            <tr style={tableHeaderStyle}>
            <th>Name</th>
            <th>Email</th>
            <th>Action</th>
            </tr>
        </thead>
        <tbody>
            {users.map((user) => (
            <tr key={user.id} style={tableRowStyle}>
                <td>{user.username}</td>
                <td>{user.email}</td>
                <td>
                <button onClick={() => deleteUser(user.id)} style={deleteButtonStyle}>
                    Delete
                </button>
                </td>
            </tr>
            ))}
        </tbody>
        </table>
        {/* Pagination Controls */}
        <div style={paginationStyle}>
        <button
            disabled={currentPage2 === 1}
            onClick={() => setCurrentPage((prev) => Math.max(prev - 1, 1))}
            style={paginationButtonStyle}
        >
            Previous
        </button>
        <span>Page {currentPage2} of {Math.ceil(totalUsers / currentSize2)}</span>
        <button
            disabled={currentPage2 === Math.ceil(totalUsers / currentSize2)}
            onClick={() => setCurrentPage((prev) => Math.min(prev + 1, Math.ceil(totalUsers / currentSize2)))}
            style={paginationButtonStyle}
        >
            Next
        </button>
        </div>
    </div>
  );
}

// Styling

const containerStyle = {
  backgroundColor: "#f0f4f8",
  padding: "30px",
  fontFamily: "'Roboto', sans-serif",
  maxWidth: "85%",
  margin: "0 auto",
  borderRadius: "10px",
};

const headerStyle = {
  fontSize: "36px",
  color: "#333",
  textAlign: "center",
  marginBottom: "40px",
  textTransform: "uppercase",
};

const statsStyle = {
  display: "flex",
  justifyContent: "space-between",
  marginBottom: "30px",
};

const statCardStyle = {
  backgroundColor: "#fff",
  padding: "15px 30px",
  borderRadius: "8px",
  boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)",
  width: "30%",
  textAlign: "center",
};

const formTitleStyle = {
  fontSize: "24px",
  marginBottom: "20px",
};

const formStyle = {
  backgroundColor: "#fff",
  padding: "20px",
  borderRadius: "8px",
  boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)",
  marginBottom: "40px",
};

const inputStyle = {
  padding: "12px",
  margin: "10px 0",
  borderRadius: "5px",
  border: "1px solid #ccc",
  width: "100%",
  maxWidth: "400px",
  boxSizing: "border-box",
  fontSize: "16px",
};

const textareaStyle = {
  padding: "12px",
  margin: "10px 0",
  borderRadius: "5px",
  border: "1px solid #ccc",
  width: "100%",
  height: "120px",
  fontSize: "16px",
};

const buttonStyle = {
  padding: "12px 30px",
  backgroundColor: "#4CAF50",
  color: "#fff",
  border: "none",
  borderRadius: "5px",
  fontSize: "16px",
  cursor: "pointer",
  marginTop: "15px",
  transition: "background-color 0.3s",
};

const rowStyleEven = {
  backgroundColor: "#fff",
};

const rowStyleOdd = {
  backgroundColor: "#f9f9f9",
};

const imageCellStyle = {
  width: "50px",
  height: "50px",
  objectFit: "cover",
  borderRadius: "50%",
};

const imagePreviewStyle = {
    width: "150px",
    height: "auto",
    marginTop: "10px",
    borderRadius: "8px",
    boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)",
};

const tableTitleStyle = { textAlign: "center", margin: "20px 0" };
const tableStyle = { width: "100%", borderCollapse: "collapse" };
const tableHeaderStyle = { background: "#f2f2f2", textAlign: "left" };
const tableRowStyle = { borderBottom: "1px solid #ddd" };
const deleteButtonStyle = { backgroundColor: "red", color: "white", border: "none", cursor: "pointer",  borderRadius: "5px"};
const paginationStyle = {
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    marginTop: "20px",
    gap: "10px",
  };
  
  const paginationButtonStyle = {

    padding: "8px 8px",
    fontSize: "14px",
    backgroundColor: "#007bff",
    color: "#fff",
    border: "none",
    borderRadius: "4px",
    cursor: "pointer",
    transition: "background-color 0.3s ease",
  };
  
export default AdminDashboard;
