import React, { useState } from 'react';
import './Header.css';
import { HOME } from '../../constants/Constants';

const Header = () => {
  const [searchCategory, setSearchCategory] = useState('Books');
  const [searchQuery, setSearchQuery] = useState('');
  const token = localStorage.getItem("token");

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
            `http://localhost:8081/search/${searchCategory}?query=${encodeURIComponent(searchQuery)}`, {
              method: 'GET',
              headers: {"Authorization": `Bearer ${token}`},
          }
        );
        console.log(searchCategory);
        console.log(searchQuery);
        const results = await response.json();
        console.log(results);
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
        <a href={HOME} className="header-link">Home</a>
        <a href={HOME} className="header-link">Books</a>
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
    </div>
    <div className="header-user">
        {/* Take first letter of Username or uploaded photo from user*/}
        <div className="header-avatar">A</div>
    </div>
  </div>
  );
};

export default Header;