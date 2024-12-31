import { Button } from "@mui/material";
import {AiFillAmazonCircle} from "react-icons/ai";

function OfferButton({ url, price }) {
    const offerName = url.split("/")[3];
    return (
        <Button
            variant="outlined"
            href={url}
            target="_blank"
            sx={{
                textTransform: "none",
                display: "flex",
                justifyContent: "space-between",
                px: 2,
            }}
        >
            <span style={{ display: 'flex' , flexDirection:'row', alignItems:'center'}}>
                <AiFillAmazonCircle size={25}/>
                {offerName}
            </span>
            <span>${price}</span>
        </Button>
    );
}

export default OfferButton;
