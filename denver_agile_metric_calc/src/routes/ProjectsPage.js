// ProjectsPage.js
import React, { Fragment, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Link, useSearchParams } from 'react-router-dom';
import '../components/ProjectComponent.css';
import { getImageUrl } from '../components/utils.js';

const ProjectsPage = () => {
	/* const [projects, setProjects] = useState([]);
	let [searchParams] = useSearchParams(); */
	const { memberID } = useParams()
  	console.log("memberID= " + memberID);
  	const getProjects = async () => {
    const projectsUrl = `http://localhost:8080/api/projects/${memberID}`
      const projectsResponse = await fetch(projectsUrl, {method: 'GET'})
      if (projectsResponse.ok) {
        const projects = await projectsResponse.json()
        return projects
      } else {
        throw projectsResponse
      }
  	}
	const projectList = getProjects()
	console.log(projectList)

/* 	useEffect(() => {
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
	}, []); */

	const formatDate = (dateString) => {
		const options = { year: 'numeric', month: 'long', day: 'numeric' };
		return new Date(dateString).toLocaleDateString(undefined, options);
	};


	return (
		<div>
			<div className='project-list-heading'>User Projects</div>
			{/* <div className='project-list'>{projectList}</div> */}
		</div>
	);
}

export default ProjectsPage