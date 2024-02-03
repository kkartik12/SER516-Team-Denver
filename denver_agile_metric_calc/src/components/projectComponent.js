// projectComponent.js
import React, { Fragment } from 'react';
import './ProjectComponent.css';
import { projects } from './data.js';
import { getImageUrl } from './utils.js';

export default function ProjectComponent() {
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
  return <div className="project-list">{listItems}</div>;
}
