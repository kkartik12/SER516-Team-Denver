import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/LoginPage.css';

function AuthenticationForm() {
	const [username, setUsername] = useState('');
	const [password, setPassword] = useState('');
	const [errorMessage, setErrorMessage] = useState('');

	const navigate = useNavigate();

	const handleSubmit = async (e) => {
		e.preventDefault();
		const loginUrl = `http://localhost:8080/api/login?username=${username}&password=${password}`
		const loginResponse = await fetch(loginUrl, {method: 'POST'})
		if (loginResponse.ok) {
			const memberID = await loginResponse.text()
			const projectsUrl = `http://localhost:8080/api/projects/${memberID}`
			const projectsResponse = await fetch(projectsUrl, {method: 'GET'})
			console.log(projectsResponse)
			if (projectsResponse.ok) {
				const projects = await projectsResponse.json()
				console.log(projects)
			}
		}
		if (loginResponse !== 'Invalid Credentials') {
			navigate('/projects')
		} else {
			setErrorMessage('Invalid username or password')
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
