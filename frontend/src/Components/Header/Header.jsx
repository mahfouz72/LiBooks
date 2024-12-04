import React from 'react';
import './Header.css';

const Header = () => {
  return (
    <div className="header">
      <div className="header-logo">
        <h1>LIBOOKS</h1>
      </div>
      <div className="header-menu">
        <a href="/home" className="header-link">Home</a>
        <a href="/books" className="header-link">Books</a>
        <div className="header-user">
          <div className="header-avatar">A</div>
          <span className="header-username">Ahmed</span>
        </div>
      </div>
    </div>
  );
};

export default Header;
