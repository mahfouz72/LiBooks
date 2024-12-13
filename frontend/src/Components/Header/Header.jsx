import React from 'react';
import './Header.css';
import { HOME } from '../../constants/Constants';

const Header = () => {
  return (
    <div className="header">
      <div className="header-logo">
        <h1>LIBOOKS</h1>
      </div>
      <div className="header-menu">
        <a href={HOME} className="header-link">Home</a>
        <a href={HOME} className="header-link">Books</a>
        <div className="header-user">
          {/* Take first letter of Username or uploaded photo from user*/}
          <div className="header-avatar">A</div>
        </div>
      </div>
    </div>
  );
};

export default Header;