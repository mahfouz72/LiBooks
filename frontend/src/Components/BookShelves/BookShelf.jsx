import "./BookShelves.css";
import { useState } from "react";
import EditIcon from '@mui/icons-material/Edit';

function BookShelfItem({ id, name, numberOfBooks, onRequestModal, onShelfClick }) {

    const [isHovered, setIsHovered] = useState(false);

    return (
        <li 
            className="bookShelfItem" 
            key={id}
            onMouseEnter={() => setIsHovered(true)}
            onMouseLeave={() => setIsHovered(false)}
            onClick={() => onShelfClick(id)}
        >
            <p className="shelfName">{name}</p>
            {isHovered? 
                <EditIcon className="editIcon" onClick={(e) => {
                    e.stopPropagation();
                    onRequestModal(id)
                    }}
                /> 
                :
                <p className="numberOfBooks">{numberOfBooks} books</p>
            }
        </li>
    );
}

export default BookShelfItem;