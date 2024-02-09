// ProjectDetails.js
import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { getImageUrl } from '../components/utils';

const ProjectDetails = () => {
  const [project, setProject] = useState(null);
  const { projectId } = useParams();

  useEffect(() => {
    const fetchProjectDetails = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/projects/${projectId}`);
        if (response.ok) {
          const data = await response.json();
          setProject(data);
        } else {
          throw new Error('Failed to fetch project details');
        }
      } catch (error) {
        console.error('Error fetching project details:', error);
      }
    };

    fetchProjectDetails();
  }, [projectId]);

  if (!project) {
    return <div>Loading...</div>;
  }

  return (
    <div className="project-details-page">
      <div className="project-list-heading">{project.projectName}</div>
      <div className="project-details-container">
        <div className="project-details">
          <p>Description: {project.description}</p>
          <p>Slug: {project.slug}</p>
          {/* Add more project details as needed */}
        </div>
      </div>
    </div>
  );
};

export default ProjectDetails;
