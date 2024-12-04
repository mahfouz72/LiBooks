import {
    AppBar,
    Avatar,
    Box,
    Button,
    IconButton,
    Stack,
    Toolbar,
    Typography,
} from "@mui/material";

function Header() {
    return (
        <Box sx={{ flexGrow: 1 }}>
            <AppBar  sx={{ bgcolor: "#974903" }}>
                <Toolbar>
                    <IconButton
                        size="small"
                        edge="start"
                        color="inherit"
                        aria-label="menu"
                        sx={{ mr: 20 }}
                    >
                        <Typography
                            variant="h6"
                            fontWeight={"bold"}
                            component="div"
                            sx={{ flexGrow: 1 }}
                        >
                            LIBOOKS
                        </Typography>
                    </IconButton>

                    <Stack
                        direction="row"
                        spacing={4}
                        sx={{ flexGrow: 1 }}
                        justifyContent={"space-around"}
                    >
                        <Button color="inherit">Home</Button>
                        <Button color="inherit">Books</Button>
                        <Stack direction="row" spacing={2}>
                            <Button
                                color="inherit"
                                sx={{
                                    '& .MuiTouchRipple-root': {
                                        borderRadius: '50%',
                                    },
                                    "&:hover": {
                                        borderRadius: '50%',
                                    },
                                }}
                            >
                                <Avatar>A</Avatar>
                            </Button>
                            <Typography variant="h6" component="div">
                                Ahmed
                            </Typography>
                        </Stack>
                    </Stack>
                </Toolbar>
            </AppBar>
        </Box >
    );
}
export default Header;
