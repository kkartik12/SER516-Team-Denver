import ArrowForwardIcon from '@mui/icons-material/ArrowForward';
import {
	Box,
	CircularProgress,
	Container,
	Divider,
	IconButton,
	List,
	ListItem,
	TextField,
	Typography,
} from '@mui/material';
import React, { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import Header from '../components/Header';

const ProjectsPage = () => {
	const { memberID } = useParams();
	const [projectList, setProjectList] = useState([]);
	const [slug, setSlug] = useState('');
	const [isLoading, setIsLoading] = useState(true);
	const navigate = useNavigate();

	// Fetches project list for a logged-in user
	useEffect(() => {
		const getProjects = async () => {
			try {
				setIsLoading(true);
				const projectsUrl = `http://localhost:8080/api/projectList/${memberID}`;
				const projectsResponse = await fetch(projectsUrl, { method: 'GET' });
				if (projectsResponse.ok) {
					const projects = await projectsResponse.json();
					const projectsWithDetails = await Promise.all(
						projects.map(async (project) => {
							const response = await fetch(
								`http://localhost:8080/api/projects/${project.projectID}`
							);
							if (response.ok) {
								const projectDetails = await response.json(); //remove any null projects
								return { ...project, details: projectDetails };
							} else {
								console.error(
									`Error fetching project details for project ID ${project.projectID}`
								);
								return null;
							}
						})
					);
					setIsLoading(false);
					const validProjects = projectsWithDetails.filter(
						(project) => project !== null
					);
					setProjectList(validProjects);
				} else {
					throw projectsResponse;
				}
			} catch (error) {
				setIsLoading(false);
				console.error('Error fetching projects:', error);
			}
		};

		getProjects();
	}, [memberID]);

	const handleSubmit = (e) => {
		e.preventDefault();
		navigate(`/projects/by-slug/${slug}`);
	};

	return (
		<div>
			{isLoading && (
				<div style={{ display: 'flex', justifyContent: 'center' }}>
					<CircularProgress />
				</div>
			)}
			{!isLoading && (
				<Container maxWidth="xl">
					<Header title="User Projects" sx={{ pt: 0, pl: 0, pr: 0 }} />

					<Typography variant="h5" align="center" sx={{ mb: 2 }}>
						Get Project By Slug
					</Typography>
					<form onSubmit={handleSubmit}>
						<Box
							sx={{
								display: 'flex',
								alignItems: 'center',
								justifyContent: 'center',
								mb: 2,
							}}
						>
							<TextField
								label="Slug"
								name="slug"
								value={slug}
								onChange={(e) => setSlug(e.target.value)}
							/>
							<IconButton type="submit" sx={{ ml: 1 }}>
								<ArrowForwardIcon />
							</IconButton>
						</Box>
					</form>

					<Divider />

					<List>
						{projectList.map((project) => (
							<ListItem
								key={project.details.projectID}
								component={Link}
								button
								to={`/projects/${project.details.projectID}`}
								sx={{ mb: 2 }}
							>
								<Box>
									<Typography variant="h6" fontWeight="bold">
										{project.details.projectName}
									</Typography>
									<Typography variant="body1">
										{project.details.description}
									</Typography>
								</Box>
							</ListItem>
						))}
					</List>
				</Container>
			)}
		</div>
	);
};

export default ProjectsPage;
