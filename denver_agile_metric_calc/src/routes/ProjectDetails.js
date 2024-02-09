// ProjectDetails.js
import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { getImageUrl } from '../components/utils';
import '../styles/ProjectDetails.css'
import SsidChartIcon from '@mui/icons-material/SsidChart';
import { Button, Icon } from '@mui/material';


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
          console.log('Data', data);  
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
    return (
      <div className="app">
        <header>
          {isLoading && <div className="loading"><div className="spinner"></div></div>}
        </header>
      </div>
    )
  }

  return (
    <div className="app">
      <header>
        <div className="title-container">
          <h1 style={{ fontWeight: 'bold', textAlign: 'left' }} className='project-title'>{project.projectName}</h1>
          <p style={{ backgroundColor: '#568c8c', padding: '10px', margin: '0', textAlign: 'left' }}>
            {project.description}
          </p>
          <p style={{ backgroundColor: '#568c8c', padding: '10px', margin: '0', textAlign: 'left' }}>
            Created At: {project.createdDate}
          </p>
          <p style={{ backgroundColor: '#568c8c', padding: '10px', margin: '0', textAlign: 'left' }}>
            Owner: {project.owner}
          </p>
        </div>
      </header>
      <main>
          <div className="content-container">
              <div className="content">
              <p style={{marginLeft: '2em'}}>Members:</p>
              <ul style={{marginLeft: '2em'}}>
              {project.members.map((member) => (
                <li key={member}>{member}</li>
              ))}
              </ul>
              <p style={{marginTop: '2em', marginLeft: '2em'}}>Milestones:</p>
              <ul style={{marginLeft: '2em'}}>
              {project.milestones.map((milestone) => (
                <li key={milestone}>{milestone}</li>
              ))}
              </ul>            
              <Button  
                variant='contained' 
                startIcon={<SsidChartIcon />}
                sx={{
                  backgroundColor: '#568c8c',
                  marginTop: '2em',
                  ":hover":{
                    backgroundColor: '#3b6363',
                  }
                }}>View Burndown Chart</Button>
              {/* Rest of your content */}
            </div>
            {/* ... */}
          </div>
      </main>
    </div>
  );
};

export default ProjectDetails;
