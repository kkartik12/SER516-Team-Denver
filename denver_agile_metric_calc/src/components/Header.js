import React from 'react'
import { 
    AppBar,
    Box,
    Toolbar,
    Typography,
 } from '@mui/material'

const Header = ({ title }) => {
    return (
        <Box sx={{ flexGrow: 1 }}>
            <AppBar position="static" sx = {{ bgcolor: '#568c8c'}}>
                <Toolbar>
                    <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                        {title}
                    </Typography>
                </Toolbar>
            </AppBar>
      </Box>
    )
}

export default Header;