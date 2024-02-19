import React, { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import '../components/ProjectComponent.css';
import {
  Box,
  AppBar,
  Toolbar,
  Typography,
  CircularProgress, 
  List,
  ListItem,
  ListItemText,
  ListItemSecondaryAction,
 } from '@mui/material';
import Header from '../components/Header';
import AnalyticsIcon from '@mui/icons-material/Analytics';

const ProjectsPage = () => {
  const { memberID } = useParams();
  const [projectList, setProjectList] = useState([]);
  const title = "User Projects"

  useEffect(() => {
    const getProjects = async () => {
      try {
        const projectsUrl = `http://localhost:8080/api/projectList/${memberID}`;
        const projectsResponse = await fetch(projectsUrl, { method: 'GET' });

        if (projectsResponse.ok) {
          const projects = await projectsResponse.json();

          // Fetch project details for all projects
          const projectsWithDetails = await Promise.all(
            projects.map(async (project) => {
              const response = await fetch(`http://localhost:8080/api/projects/${project.projectID}`);
              if (response.ok) {
                const projectDetails = await response.json();
                return { ...project, details: projectDetails };
              } else {
                console.error(`Error fetching project details for project ID ${project.projectID}`);
                return null;
              }
            })
          );

          // Remove projects with null details
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
      <List dense component="nav"> 
      {projectList.map((project) => (
        <ListItem key={project.details.projectID} button component={Link} to={`/projects/${project.details.projectID}`}>
          <AnalyticsIcon />
          <ListItemText primary={project.details.projectName} />
          <ListItemSecondaryAction>
            <p>{project.details.description}</p>
          </ListItemSecondaryAction>
        </ListItem>
      ))}
    </List>
    </Box>

  );
};

export default ProjectsPage;

