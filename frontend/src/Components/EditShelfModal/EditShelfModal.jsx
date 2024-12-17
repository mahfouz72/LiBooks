import "./EditShelfModal.css";
import { useState } from "react";

function EditShelfModal({onCancel, onRename, onDelete, name}) {
    
    const [newName, setNewName] = useState("");

    return (
        <div className="modalWrapper">
            <div className="modal">
                <h2>Edit Bookshelf</h2>
                <input 
                    className="inputRename"
                    type="text" 
                    value={newName}
                    onChange={(e) => setNewName(e.target.value)}
                    placeholder = {name}
                />
                <div className="buttons">
                    <button className="rename shelfModalButton" onClick={() => onRename(newName)}>Rename</button>
                    <button className="cancel shelfModalButton" onClick={onCancel}>Cancel</button>
                    <button className="delete shelfModalButton" onClick={onDelete}>Delete</button>
                </div>
            </div>
        </div>
    )
}

export default EditShelfModal;