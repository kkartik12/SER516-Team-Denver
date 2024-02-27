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
  Box,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button } from '@mui/material';
import MetricsSection from '../components/Metrics';
import PersonIcon from '@mui/icons-material/Person';
import Header from '../components/Header';

const ProjectDetails = () => {
  const [isLoading, setIsLoading] = useState(true)
  const [project, setProject] = useState(null);
  const [errorDialogOpen, setErrorDialogOpen] = useState(false);
  const { projectId , slug} = useParams();
  console.log(projectId)
  let apiURL;
  if (projectId) {
    apiURL = `http://localhost:8080/api/projects/${projectId}`; // Use projectId if available
  } else if (slug) {
    apiURL = `http://localhost:8080/api/projects/by-slug/${slug}`; // Use slug if not
  }
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
        setErrorDialogOpen(true); 
      } finally {
        setIsLoading(false);
      }
    };

    fetchProjectDetails();
  }, [projectId]);

  const handleDialogClose = () => {
    setErrorDialogOpen(false);
  };

  if (!project) {
    return (
      <div style = {{position: 'absolute', 
      top: '50%', 
      left: '50%', 
      transform: 'translate(-50%, -50%)',}}>
          {isLoading && <CircularProgress />}
          <Dialog open={errorDialogOpen} onClose={handleDialogClose}>
            <DialogTitle>Error</DialogTitle>
            <DialogContent>
              <Box>
                Failed to fetch project details. Please check the slug or try again later.
              </Box>
            </DialogContent>
            <DialogActions>
              <Button onClick={handleDialogClose} color="primary">
                OK
              </Button>
            </DialogActions>
          </Dialog>
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
