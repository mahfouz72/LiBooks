import {Card, CardMedia} from "@mui/material";


function BookCover({bookCover, bookTitle}) {
    return (
        <Card elevation={0}>
            <CardMedia
                component="img"
                image={`data:image/jpeg;base64,${bookCover}`}
                alt={bookTitle}
                sx={{height: "450px", width:"100%", objectFit: "contain", backgroundColor: "#f5f0e4",}}
            />
        </Card>
    );
}

export default BookCover;