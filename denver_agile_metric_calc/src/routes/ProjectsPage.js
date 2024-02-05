// ProjectsPage.js
import React, { Fragment } from 'react';
import '../components/ProjectComponent.css';
import { projects } from '../components/data.js';
import { getImageUrl } from '../components/utils.js';

export default function ProjectsPage() {
  const listItems = projects.map(project => (
    <Fragment key={project.id}>
        <div className="project-item">
            <img className="project-image" src={getImageUrl(project)} alt={project.name} />
            <div className="project-details">
            <b>{project.name}</b>
            <p>{project.description}</p>
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
