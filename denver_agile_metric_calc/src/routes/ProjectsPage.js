// ProjectsPage.js
import React, { Fragment } from 'react';
import { Link } from 'react-router-dom';
import '../components/ProjectComponent.css';
import { projects } from '../components/data.js';
import { getImageUrl } from '../components/utils.js';

export default function ProjectsPage() {
  const formatDate = (dateString) => {
    const options = { year: 'numeric', month: 'long', day: 'numeric' };
    return new Date(dateString).toLocaleDateString(undefined, options);
  };

  const listItems = projects.map(project => (
    <Fragment key={project.id}> 
        <div className="project-item">
            <img className="project-image" src={getImageUrl(project)} alt={project.name} />
            <div className="project-details">
            <Link to={`/projects/${project.id}`} className="project-link">
            <b>{project.name}</b>
            </Link>
            <p>{project.description}</p>
            <p>Created on: {formatDate(project.created_date)}</p>
            </div>
        </div>
    </Fragment>
  ));

  return (
    <div>
            <div className="project-list-heading">User Projects</div>
            <div className="project-list">{listItems}</div>
    </div>
  );
}
