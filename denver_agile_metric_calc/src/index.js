import React from 'react';
import './index.css';
import App from './App';
import LoginPage from './routes/LoginPage'
import ProjectsPage from './routes/ProjectsPage'
import {
  createBrowserRouter,
  RouterProvider,
} from "react-router-dom"
import  ReactDOM  from 'react-dom/client';

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
  },
  {
    path: "/projects",
    element: <ProjectsPage />,
  }
])

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <RouterProvider router = {router} />
  </React.StrictMode>
)
