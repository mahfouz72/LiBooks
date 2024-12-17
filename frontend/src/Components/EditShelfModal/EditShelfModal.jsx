import "./EditShelfModal.css";
import { useState } from "react";

function EditShelfModal({onCancel, onRename, onDelete, name}) {
    
    const [newName, setNewName] = useState("");

    return (
        <div className="modalWrapper">
            <div className="modal">
                <h2>Edit Bookshelf</h2>
                <input 
                    type="text" 
                    value={newName}
                    onChange={(e) => setNewName(e.target.value)}
                    placeholder = {name}
                />
                <div className="buttons">
                    <button className="rename" onClick={() => onRename(newName)}>Rename</button>
                    <button className="cancel" onClick={onCancel}>Cancel</button>
                    <button className="delete" onClick={onDelete}>Delete</button>
                </div>
            </div>
        </div>
    )
}

export default EditShelfModal;