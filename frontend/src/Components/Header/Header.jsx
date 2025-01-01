import React, { useState, useEffect } from 'react';
import './Header.css';
import { HOME, PROFILE } from '../../constants/Constants';
import { useNavigate } from 'react-router-dom';
import ProfileAvatar from '../ProfileAvatar/ProfileAvatar';


const Header = () => {
  const navigate = useNavigate();
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [searchCategory, setSearchCategory] = useState('Books');
  const [searchQuery, setSearchQuery] = useState('');

  const token = localStorage.getItem("token");
  
  useEffect(() => {
            const getUserName = async () => {
                const response = await fetch(
                        `http://localhost:8080/username`, {
                        method: 'GET',
                        headers: {"Authorization": `Bearer ${token}`},
                    }
                );
                const data = await response.json();
                setFirstName(data.username)
                setLastName(data.username)
            };
            getUserName();
        }, [firstName,lastName, token]);

  const handleCategoryChange = (event) => {
    setSearchCategory(event.target.value);
  };

  const handleSearchChange = (event) => {
    setSearchQuery(event.target.value);
  };

  const handleSearchSubmit = async (event) => {
    event.preventDefault();
    try {
        const response = await fetch(
            `http://localhost:8080/search/${searchCategory}?query=${encodeURIComponent(searchQuery)}`, {
              method: 'GET',
              headers: {"Authorization": `Bearer ${token}`},
          }
        );
        console.log(searchCategory);
        console.log(searchQuery);
        const results = await response.json();
        console.log(results);
        navigate(`/search/${searchCategory}?query=${encodeURIComponent(searchQuery)}`, { state: { results } });
        // Handle results (e.g., display them in the UI)
    } catch (error) {
        console.error("Error fetching search results:", error);
    }
};

  
  return (
    <div className="header">
      <div className="header-logo">
        <h1>LIBOOKS</h1>
      </div>
      <div className="header-menu">
        <div className="header-search">
        <form onSubmit={handleSearchSubmit} className="search-form">
          <select
            value={searchCategory}
            onChange={handleCategoryChange}
            className="search-category"
          >
            <option value="Books">Books</option>
            <option value="Authors">Authors</option>
            <option value="Users">Users</option>
          </select>
          <input
            type="text"
            value={searchQuery}
            onChange={handleSearchChange}
            placeholder={`Search ${searchCategory}`}
            className="search-input"
          />
          <button type="submit" className="search-button">Search</button>
        </form>
      </div>
        <a href={HOME} className="header-link">Home</a>
        <a href={"/BookBrowsingPage"} className="header-link">Books</a>
           <div className="header-user">
                    {/* Take first letter of Username or uploaded photo from user */}
                    <a href={PROFILE} className="header-link">
                        <ProfileAvatar firstName={firstName} lastName={lastName} size="small" />
                    </a>
            </div>
    </div>
  </div>
  );
};
export default Header;