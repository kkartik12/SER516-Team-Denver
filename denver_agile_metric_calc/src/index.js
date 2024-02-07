import React from 'react';
import './index.css';
import App from './App';
import ProjectsPage from './routes/ProjectsPage';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import ReactDOM from 'react-dom/client';
import ProjectDetails from './routes/ProjectDetails';

const router = createBrowserRouter([
	{
		path: '/',
		element: <App />,
	},
	{
		path: '/projects/:memberID',
		element: <ProjectsPage />,
	},
	{
		path: '/projects/:projectId',
		element: <ProjectDetails />,
	},
]);

ReactDOM.createRoot(document.getElementById('root')).render(
	<React.StrictMode>
		<RouterProvider router={router} />
	</React.StrictMode>
);
