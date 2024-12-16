import "./BookShelves.css";
import { useState } from "react";
import EditIcon from '@mui/icons-material/Edit';

function BookShelfItem({ key, name, numberOfBooks }) {

    const [isHovered, setIsHovered] = useState(false);

    return (
        <li 
            className="bookShelfItem" 
            key={key}
            onMouseEnter={() => setIsHovered(true)}
            onMouseLeave={() => setIsHovered(false)}
        >
            <p className="shelfName">{name}</p>
            {isHovered? 
                <EditIcon className="editIcon" /> 
                :
                <p className="numberOfBooks">{numberOfBooks} books</p>
            }
        </li>
    );
}

export default BookShelfItem;