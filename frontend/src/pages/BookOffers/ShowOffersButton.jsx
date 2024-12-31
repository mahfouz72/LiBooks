import React, {useState} from "react";
import {Box, Button, Dialog, Skeleton, Stack, Typography} from "@mui/material";
import OfferButton from "./OfferButton";

function ShowOffersButton({bookId}) {
    const [loading, setLoading] = useState(false);
    const [offers, setOffers] = useState([]);
    const [dialogOpen, setDialogOpen] = useState(false);

    const fetchOffers = async () => {
        setLoading(true);
        try {
            const response = await fetch(
                `http://localhost:8080/offers/${bookId}`,
                {
                    method: 'GET',
                    headers: {"Authorization": `Bearer ${localStorage.getItem("token")}`},
                }
            )
            if (!response.ok) {
                throw new Error("Failed to fetch offers");
            }
            const data = await response.json();
            console.log(data)
            setOffers(data);
        } catch (error) {
            console.error("Error fetching offers:", error);
        } finally {
            setLoading(false);
        }
    };

    const handleShowOffers = () => {
        setDialogOpen(true);
        fetchOffers();
    };

    const handleClose = () => {
        setDialogOpen(false);
    };

    return (
        <>
            <Button
                variant="contained"
                sx={{
                    textTransform: "none",
                    backgroundColor: "black",
                    color: "white",
                    mt: 2,
                    px: 5,
                }}
                onClick={handleShowOffers}
            >
                Show Offers
            </Button>

            <Dialog open={dialogOpen} onClose={handleClose} maxWidth="sm" fullWidth>
                <Box sx={{p: 4}}>
                    <Typography variant="h6" mb={2}>Offers</Typography>
                    {loading ? (
                        <Stack spacing={2}>
                            <Skeleton variant="rectangular" height={40}/>
                            <Skeleton variant="rectangular" height={40}/>
                            <Skeleton variant="rectangular" height={40}/>
                        </Stack>
                    ) : (
                        <Stack spacing={2}>
                            {offers.map((offer, index) => (
                                <OfferButton url={offer.url} price={offer.price} key={index} />
                            ))}
                        </Stack>
                    )}
                </Box>
            </Dialog>
        </>
    );
}

export default ShowOffersButton;
