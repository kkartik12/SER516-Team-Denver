// ProjectDetails.js
import React, {useState, useEffect, Fragment, Link} from 'react';

import { useParams, useSearchParams } from 'react-router-dom';
import { projects } from '../components/data';
import { getImageUrl } from '../components/utils';

const ProjectDetails = () => {
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
  const { projectId } = useParams();
  const selectedProject = projects.find(project => project.id === parseInt(projectId, 10));

  if (!selectedProject) {
    return <div>No project found with the given ID.</div>;
  }

  return (
    <div>
    <div className="project-list-heading">{selectedProject.name}</div>
    <div className="project-details-container">
      <img src={getImageUrl(selectedProject)} alt={selectedProject.name} />
      <div className="project-details">
        <p>{selectedProject.description}</p>
      </div>
    </div>
    </div>
  );
};

export default ProjectDetails;
