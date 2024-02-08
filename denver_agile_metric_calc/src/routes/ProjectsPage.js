import React, { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import '../components/ProjectComponent.css';

const ProjectsPage = () => {
  const { memberID } = useParams();
  const [projectList, setProjectList] = useState([]);

  useEffect(() => {
    const getProjects = async () => {
      try {
        const projectsUrl = `http://localhost:8080/api/projectList/${memberID}`;
        const projectsResponse = await fetch(projectsUrl, { method: 'GET' });

        if (projectsResponse.ok) {
          const projects = await projectsResponse.json();

          // Fetch project details for all projects
          const projectsWithDetails = await Promise.all(
            projects.map(async (project) => {
              const response = await fetch(`http://localhost:8080/api/projects/${project.projectID}`);
              if (response.ok) {
                const projectDetails = await response.json();
                return { ...project, details: projectDetails };
              } else {
                console.error(`Error fetching project details for project ID ${project.projectID}`);
                return null;
              }
            })
          );

          // Remove projects with null details
          const validProjects = projectsWithDetails.filter((project) => project !== null);

          setProjectList(validProjects);
        } else {
          throw projectsResponse;
        }
      } catch (error) {
        console.error('Error fetching projects:', error);
      }
    };

    getProjects();
  }, [memberID]);

  return (
    <div>
      <div className='project-list-heading'>User Projects</div>
      <ul className='project-list'>
        {projectList.map((project) => (
          <li key={project.details.projectID} className="project-list-item">
            <div className="project-details">
              <h3 className="project-name">
                <Link to={`/projects/${project.details.projectID}`} className="project-link">
                  {project.details.projectName}
                </Link>
              </h3>
              <p className="project-description">{project.details.description}</p>
            </div>
          </li>
        ))}
      </ul>
    </div>

  );
};

export default ProjectsPage;

