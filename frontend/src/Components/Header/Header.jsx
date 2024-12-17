import './Header.css';
import { HOME, PROFILE } from '../../constants/Constants';
import ProfileAvatar from '../ProfileAvatar/ProfileAvatar';

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
                    {/* Take first letter of Username or uploaded photo from user */}
                    <a href={PROFILE} className="header-link">
                        <ProfileAvatar firstName="Youssif" lastName="Khaled" size="small" />
                    </a>
                </div>
            </div>
        </div>
    );
};

export default Header;