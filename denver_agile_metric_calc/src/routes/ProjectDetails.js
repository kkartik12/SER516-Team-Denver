// ProjectDetails.js
import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { getImageUrl } from '../components/utils';
import '../styles/ProjectDetails.css'


const ProjectDetails = () => {
  const [isLoading, setIsLoading] = useState(true)
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
    <div className="app">
      <header>
        <h1>{project.projectName}</h1>
      </header>
      <main>
          {/* isLoading ? (
            <div className="loading">
              <div className="spinner"></div>
            </div>
          ) : */ (
          <div className="content-container">
            <div className="content">
              Description: {project.description}
              
            </div>
            {/* <div className="sidebar">
              
              </div> */}
              <div className="buttons-container">
          <button className="button">View Burndown Chart</button>
            </div>
              
          </div>
        )}
        
      </main>
      
      <footer>
        
      </footer>
    </div>
  );
};

export default ProjectDetails;
