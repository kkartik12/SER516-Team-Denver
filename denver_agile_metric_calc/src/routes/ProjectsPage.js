// ProjectsPage.js
import React, { Fragment, useEffect, useState } from 'react';
import { Link, useSearchParams } from 'react-router-dom';
import '../components/ProjectComponent.css';
import { getImageUrl } from '../components/utils.js';

export default function ProjectsPage() {
	const [projects, setProjects] = useState([]);
	let [searchParams] = useSearchParams();

	useEffect(() => {
		fetch(
			`${
				process.env.REACT_APP_LOCAL_BASE_URL
			}/api/getProjectList/${searchParams.get('memberId')}`
		)
			.then((response) => response.json())
			.then((data) => {
				// Handle the fetched data
				console.log(data);

				setProjects(
					data.map((project) => (
						<Fragment key={project.projectId}>
							<div className='project-item'>
								<img
									className='project-image'
									src={getImageUrl(project)}
									alt={project.projectName}
								/>
								<div className='project-details'>
									<Link
										to={`/projects/${project.projectName}`}
										className='project-link'
									>
										<b>{project.projectName}</b>
									</Link>
									<p>{project.slug}</p>
								</div>
							</div>
						</Fragment>
					))
				);
			})
			.catch((error) => {
				// Handle errors
				console.error('Error fetching data:', error);
			});
	}, []);

	const formatDate = (dateString) => {
		const options = { year: 'numeric', month: 'long', day: 'numeric' };
		return new Date(dateString).toLocaleDateString(undefined, options);
	};


	return (
		<div>
			<div className='project-list-heading'>User Projects</div>
			<div className='project-list'>{projects}</div>
		</div>
	);
}
