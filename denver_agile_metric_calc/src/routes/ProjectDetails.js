// ProjectDetails.js
import React from 'react';
import { useParams } from 'react-router-dom';
import { projects } from '../components/data';
import { getImageUrl } from '../components/utils';

const ProjectDetails = () => {
  const { projectId } = useParams();
  const selectedProject = projects.find(project => project.id === parseInt(projectId, 10));

  if (!selectedProject) {
    return <div>No project found with the given ID.</div>;
  }

  return (
    <div className="project-details-container">
      <img src={getImageUrl(selectedProject)} alt={selectedProject.name} />
      <div className="project-details">
        <h2>{selectedProject.name}</h2>
        <p>{selectedProject.description}</p>
      </div>
    </div>
  );
};

export default ProjectDetails;
