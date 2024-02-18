// ProjectDetails.js
import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { 
  CircularProgress, 
  ListItem,
  ListItemAvatar,
  Avatar,
  ListItemText,
  Stack,
  Divider,
  Box } from '@mui/material';
import MetricsSection from '../components/Metrics';
import PersonIcon from '@mui/icons-material/Person';
import Header from '../components/Header';

const ProjectDetails = () => {
  const [isLoading, setIsLoading] = useState(true)
  const [project, setProject] = useState(null);
  const { projectId } = useParams();

  const apiURL = `http://localhost:8080/api/projects/${projectId}`

  useEffect(() => {
    const fetchProjectDetails = async () => {
      try {
        const response = await fetch(apiURL);
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
    return (
      <div style = {{position: 'absolute', 
      top: '50%', 
      left: '50%', 
      transform: 'translate(-50%, -50%)',}}>
          {isLoading && <CircularProgress />}
      </div>
    )
  }

  return (
    <Box>
      <Header title = {project.projectName}/>
        <Box sx = {{ml: 2}}>
          <h2>Members:</h2>          
            <Stack direction="row" spacing={1} divider={<Divider orientation="vertical" flexItem />}>
              {project.members.map((member) => (
                <ListItem key={member}>
                  <ListItemAvatar>
                    <Avatar>
                      <PersonIcon />
                    </Avatar>
                  </ListItemAvatar>
                  <ListItemText primary={member} />
                </ListItem>
              ))}
            </Stack>
            <h2 style={{marginBottom: '2em'}}>Metrics:</h2>
            <Divider />
            <MetricsSection project={project}/>
        </Box>
    </Box>
  );
};

export default ProjectDetails;
