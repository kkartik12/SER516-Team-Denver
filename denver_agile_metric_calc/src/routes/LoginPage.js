import React, { useState } from 'react';
import { redirect } from 'react-router-dom';
import '../styles/LoginPage.css';

function AuthenticationForm() {
	const [username, setUsername] = useState('');
	const [password, setPassword] = useState('');
	const [errorMessage, setErrorMessage] = useState('');

	const handleSubmit = async (e) => {
		e.preventDefault();

		//backend call

		const response = await fetch(
			`${process.env.REACT_APP_LOCAL_BASE_URL}/api/login?username=${username}&password=${password}`,
			{
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
				},
			}
		);

		const data = await response.text();
		if (data !== 'Invalid Credentials') {
			redirect('/projects');
		} else {
			setErrorMessage('Invalid username or password');
		}
	};

	return (
		<div className='login-container'>
			<div className='image-container'>
				<h1>Agile Metrics</h1>
				<img
					src='https://facts.net/wp-content/uploads/2023/09/21-facts-about-lightning-mcqueen-cars-1694564602.jpg' // Replace with the actual image path
					alt='Lightning McQueen'
					className='profile-picture'
				/>
			</div>
			<form onSubmit={handleSubmit}>
				<input
					type='text'
					placeholder='Username'
					id='username'
					value={username}
					onChange={(e) => setUsername(e.target.value)}
				/>
				<br />
				<input
					type='password'
					placeholder='Password'
					id='password'
					value={password}
					onChange={(e) => setPassword(e.target.value)}
				/>
				<br />
				{errorMessage && <p className='error'>{errorMessage}</p>}
				<button type='submit'>LOG IN</button>
			</form>
		</div>
	);
}

export default AuthenticationForm;
