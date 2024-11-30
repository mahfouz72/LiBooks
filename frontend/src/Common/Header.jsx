import {AppBar, Avatar, Box, IconButton, Stack, Toolbar, Typography} from "@mui/material";

function Header(){
    return(
        <Box sx={{ flexGrow: 1  }}>
            <AppBar position="static" sx={{bgcolor:'#974903'}}>
                <Toolbar>
                    <IconButton
                        size="small"
                        edge="start"
                        color="inherit"
                        aria-label="menu"
                        sx={{ mr: 20}}
                    >
                        <Typography variant="h6" fontWeight={"bold"} component="div" sx={{ flexGrow: 1 }}>
                            LIBOOKS
                        </Typography>
                    </IconButton>

                    <Stack direction="row" spacing={4} sx={{ flexGrow: 1 }} justifyContent={"space-around"}>
                        <Typography variant="h6" component="div" >
                            Home
                        </Typography>
                        <Typography variant="h6" component="div" >
                            Books
                        </Typography>
                        <Stack direction="row" spacing={2} >
                            <Avatar>A</Avatar>
                            <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                                Ahmed
                            </Typography>
                        </Stack>
                    </Stack>
                </Toolbar>
            </AppBar>
        </Box>
    )
}
export default Header;