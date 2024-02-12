import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { createTheme, ThemeProvider, Box, TextField, Button, Typography, Stack, Avatar, Divider, Chip } from '@mui/material'
import LoginIcon from '@mui/icons-material/Login'
import '../styles/LoginPage.css'

const logoImage = 'https://facts.net/wp-content/uploads/2023/09/21-facts-about-lightning-mcqueen-cars-1694564602.jpg';
const theme = createTheme({
  palette: {
    primary: {
      main: '#007bff', 
    },
    error: {
      main: '#dc3545', 
    },
	background: {
		default: '#757474',
	},
  },
});

function AuthenticationForm() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();
    const loginUrl = `http://localhost:8080/api/login?username=${username}&password=${password}`;
    const loginResponse = await fetch(loginUrl, { method: 'POST' });
    if (loginResponse.ok) {
      const memberID = await loginResponse.text();
      navigate(`/projectList/${memberID}`);
    } else {
      setErrorMessage('Invalid Username or Password');
    }
  };

  return (
    <ThemeProvider theme={theme}>
		<div className='login-container'>
			<Box sx={{ 
					display: 'flex', 
					justifyContent: 'center', 
					alignItems: 'center', 
					minHeight: '100vh',
					backgroundColor: 'rgb(0, 0 , 0, 0.5)' }}>
				<Stack spacing={2} 
					sx={{ 
						width: 400, 
						padding: 4,
						backgroundColor: 'white', 
						borderRadius: 5 }}>
				<Typography variant="h4" sx={{ textAlign: 'center' }}>
					Agile Metrics
				</Typography>
				<Divider>
					<Chip label="LOG IN">
						
					</Chip>
				</Divider>
				<TextField
					label="Username"
					value={username}
					onChange={(e) => setUsername(e.target.value)}
					error={!!errorMessage}
					helperText={errorMessage}
					
				/>
				<TextField
					label="Password"
					type="password"
					value={password}
					onChange={(e) => setPassword(e.target.value)}
					error={!!errorMessage}
				/>
				<Button
					variant="contained"
					color="primary"
					endIcon={<LoginIcon />}
					onClick={handleSubmit}
					sx = {{
						backgroundColor: '#568c8c',
					}}
				>
					Log In
				</Button>
				</Stack>
			</Box>
		</div>
    </ThemeProvider>
  );
}

export default AuthenticationForm;
