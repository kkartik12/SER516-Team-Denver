import React, {useState} from 'react'
import {Modal, Button, Box} from '@mui/material'
import SsidChartIcon from '@mui/icons-material/SsidChart';
import CloseIcon from '@mui/icons-material/Close';

const BurndownPopup = () => {
    const [isOpen, setIsOpen] = useState(false)
    const handleOpen = () => {setIsOpen(true)}
    const handleClose = () => {setIsOpen(false)}
    return (
        <div style={{backgroundColor: 'white'}}>
            <Button 
                variant='contained' 
                startIcon={<SsidChartIcon />}
                sx={{
                  backgroundColor: '#568c8c',
                  marginTop: '2em',
                  ":hover":{
                    backgroundColor: '#3b6363',
                  }
                }}
                onClick={handleOpen}>View Burndown Chart</Button>
            
                <Modal
                open={isOpen}
                onClose={handleClose}
                sx={{
                    backgroundColor: "rgba(255,255,255,1)",
                    position: 'absolute', 
                    top: '50%', 
                    left: '50%', 
                    transform: 'translate(-50%, -50%)',
                }}
                >
                <Box sx={{width: '100%', 
                        height: '100%', 
                        backgroundColor: '#fff',
                        }}>
                    <Box sx = {{
                        marginBottom: '1.5em',
                        backgroundColor: '#fff',
                        paddingTop: '1em',
                    }}>
                        <h2 style={{
                            textAlign: 'center',
                            }}>Burndown Chart</h2>
                        <Button
                            style={{ position: 'absolute', top: '1em', right: '1em' }}
                            onClick={handleClose}>
                            <CloseIcon />
                        </Button>
                    </Box>        
                    <Box sx={{
                        display: 'flex',
                        justifyContent: 'space-between',
                        marginLeft: '1em',
                        marginRight: '1em',
                    }}>
                        <Button variant='contained'>Partial Running Sum</Button>
                        <Button variant='contained'
                        sx = {{
                            backgroundColor: 'red',
                            ":hover":{
                                backgroundColor: '#99170e',
                              }
                        }}>Total Running Sum</Button>
                        <Button variant='contained'
                        sx = {{
                            backgroundColor: '#fac020',
                            ":hover":{
                                backgroundColor: '#ada428',
                              }
                        }}>BV Running Sum</Button>
                    </Box>
                </Box>
                </Modal>          
        </div>
    )
}

export default BurndownPopup