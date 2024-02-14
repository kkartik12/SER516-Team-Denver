import React, { useEffect, useState } from 'react';
import { Box, Paper, Typography } from '@mui/material';
import { useParams } from 'react-router-dom';

const GraphComponent = ({ sx = {} , parameter}) => {
  const { projectId } =  useParams()
  const [milestone, setMilestone] = useState({})
  //const [isLoading, setIsLoading] = useState(true)
/*   useEffect(() => {
    fetch(`http://localhost:8080/api/projects/${projectId}/${parameter}`)
      .then(response => response.json())
      .then(data => setData(data))
  }, [parameter, projectId]) */
  useEffect(() => {
    const fetchMilestoneDetails = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/projects/${projectId}/${parameter}`);
        if (response.ok) {
          const data = await response.json();
          console.log("milestone: ", data)  
          setMilestone(data);
        } else {
          throw new Error('Failed to fetch project details');
        }
      } catch (error) {
        console.error('Error fetching project details:', error);
      }
    };

    fetchMilestoneDetails();
  }, [projectId, parameter]);
  return (
    <Paper sx={{ ...sx, ...styles.container }}>
      <Typography variant="body2" color="text.secondary">
        Placeholder for Burndown Chart
      </Typography>
    </Paper>
  );
};

const styles = {
  container: {
    width: '100%',
    height: '300px',
    background: '#f5f5f5',
    borderRadius: 4,
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
  },
};

export default GraphComponent;
