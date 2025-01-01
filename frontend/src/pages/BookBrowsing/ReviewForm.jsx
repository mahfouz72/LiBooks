import { useState } from "react";
import { TextField, Rating, Button, Dialog, DialogTitle, DialogContent, DialogActions } from "@mui/material";

function ReviewForm({ onSubmit, onClose }) {
    const [newReview, setNewReview] = useState({rating: 0, reviewText: '' ,date: new Date().toISOString().slice(0, 19)});

    const handleInputChange = (e) => {
        setNewReview({ ...newReview, [e.target.name]: e.target.value });
    };

    const handleRatingChange = (e, newValue) => {
        setNewReview({ ...newReview, rating: newValue });
    };

    const handleSubmit = () => {
        onSubmit(newReview);
    };

    return (
        <Dialog open onClose={onClose}>
            <DialogTitle>Write a Review</DialogTitle>
            <DialogContent>
                <Rating
                    name="rating"
                    value={newReview.rating}
                    onChange={handleRatingChange}
                    precision={0.5}
                    size="meduim"
                    sx={{ mb: 2 }}
                />
                <TextField
                    fullWidth
                    label="Enter your review"
                    name="reviewText"
                    value={newReview.reviewText}
                    onChange={handleInputChange}
                    multiline
                    rows={4}
                    margin="normal"
                />
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose}
                         sx={{
                            textTransform: 'none',
                            backgroundColor: 'red',
                            color: 'white',
                        }}>
                    Cancel
                </Button>
                <Button onClick={handleSubmit} variant="contained"
                        sx={{
                            textTransform: 'none',
                            backgroundColor: 'black',
                            color: 'white',
                        }}>
                    Submit
                </Button>
            </DialogActions>
        </Dialog>
    );
}

export default ReviewForm;
