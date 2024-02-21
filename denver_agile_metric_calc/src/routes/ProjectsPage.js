import React, { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import {
  Box,
  Typography,
  CircularProgress, 
  List,
  ListItem,
  ListItemText,
  ListItemSecondaryAction,
  Grid,
  IconButton,
  TextField,
  Button,
  Divider
 } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import Header from '../components/Header';
import AnalyticsIcon from '@mui/icons-material/Analytics';
import ArrowForwardIcon from '@mui/icons-material/ArrowForward';

const ProjectsPage = () => {
  const { memberID } = useParams()
  const [projectList, setProjectList] = useState([])
  const [slug,  setSlug] = useState('')
  const title = "User Projects"
  const navigate =  useNavigate()

  const handleSubmit = (e) => {
    e.preventDefault()
    navigate(`/projects/by-slug/${slug}`)
  }

  //Fetches project list for a logged in user 
  useEffect(() => {
    const getProjects = async () => {
      try {
        const projectsUrl = `http://localhost:8080/api/projectList/${memberID}`;
        const projectsResponse = await fetch(projectsUrl, { method: 'GET' });
        if (projectsResponse.ok) {
          const projects = await projectsResponse.json();
          const projectsWithDetails = await Promise.all(
            projects.map(async (project) => {
              const response = await fetch(`http://localhost:8080/api/projects/${project.projectID}`);
              if (response.ok) {
                const projectDetails = await response.json(); //remove any null projects
                return { ...project, details: projectDetails };
              } else {
                console.error(`Error fetching project details for project ID ${project.projectID}`);
                return null;
              }
            })
          );
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
    <Box>
      <Header title = {title}/>
      <Grid container spacing={2}>
        <Grid item xs={8}>
          <Box>
            <List dense component="nav"> 
            {projectList.map((project) => (
                <ListItem 
                  key={project.details.projectID} 
                  component={Link} 
                  button
                  to={`/projects/${project.details.projectID}`}
                >
                <AnalyticsIcon />
                <ListItemText primary={project.details.projectName} />
                <ListItemSecondaryAction>
                  <p>{project.details.description}</p>
                </ListItemSecondaryAction>
              </ListItem>
            ))}
          </List>
          </Box>
        </Grid>
        <Divider />
        <Grid item xs={4} sx = {{mt: 1}}>
            <Typography sx = {{mt: 1, mb: 1}}>
              Get Project By Slug
            </Typography>
            <form onSubmit={handleSubmit}>
              <Box sx={{ display: 'flex', alignItems: 'center' }}>
                  <TextField
                    label="Slug"
                    name="slug"
                    value={slug}
                    onChange={(e) => setSlug(e.target.value)}
                    fullWidth
                  />
                  <IconButton type="submit" sx={{ ml: 1 , mr: 1, mb: 2.5}}>
                    <ArrowForwardIcon />
                  </IconButton>
              </Box>
            </form>
        </Grid>
      </Grid>
    </Box>
    
  );
};

export default ProjectsPage;

