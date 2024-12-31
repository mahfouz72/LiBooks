import {
    Box,
    Card,
    CardActions,
    CardContent,
    Skeleton,
  } from "@mui/material";
  
  function LoadingBookCard() {
       
    return (
      <Card sx={{ position: "relative", width: 175, height: 350, cursor: "pointer",}}>
        <Skeleton animation="wave" variant="rectangular" width="100%" height="200px"/>
        <CardContent>
            <Skeleton animation="wave" variant="rectangular" width="143px" height="20px"/>
            <Box width="100%" height="20px"/>
            <Skeleton animation="wave" variant="rectangular" width="143px" height="20px"/>
        </CardContent>
        <CardActions sx={{position: 'absolute' , bottom: 0, m:'4px'}}>
            <Skeleton variant="rectangular" width="136px" height="20px"/>
        </CardActions>
      </Card>
    );
  }
  export default LoadingBookCard;
  