import React, { useEffect, useState, useRef } from 'react';
import { Box, Paper, Typography } from '@mui/material';
import { useParams } from 'react-router-dom';
import Chart from 'chart.js/auto';

const GraphComponent = ({ sx = {} , parameter}) => {
  const { projectId } =  useParams()
  const [milestone, setMilestone] = useState({})
  const BvchartInstance = useRef(null); // Define BvchartInstance using useRef
  //const [isLoading, setIsLoading] = useState(true)
  useEffect(() => {
    fetch(`http://localhost:8080/api/burndownchart/${projectId}/${parameter}`)
      .then(response => response.json())
      // .then(data => setData(data))
  }, [parameter, projectId])
   useEffect(() => {
    const fetchMilestoneDetails = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/projects/${projectId}`);
        if (response.ok) {
          const data = await response.json();
          console.log("project: ", data);
          // console.log("milestone: ", data)  
          // setMilestone(data);
        } else {
          throw new Error('Failed to fetch project details');
        }
      } catch (error) {
        console.error('Error fetching project details:', error);
      }
    };

    fetchMilestoneDetails();
    if(parameter === "BusinessValue"){
    // Dummy data for the line graph
    const labels = ['29/01/2024', '08/02/2024', '12/02/2024', '14/02/2024', '20/02/2024'];
    const data = {
      labels: labels,
      datasets: [{
        label: 'Burndown Chart',
        data: [2, 4, 6, 8, 10], 
        borderColor: 'rgba(75, 192, 192, 1)',
        borderWidth: 1,
        fill: false
      }]
    };
  
    // Get the canvas element
    const ctx = document.getElementById('burndownChart');
  
    // Destroy existing chart instance if it exists
    if (BvchartInstance.current) {
      BvchartInstance.current.destroy();
    }
  
    // Render the line graph using Chart.js
    if (ctx) {
      BvchartInstance.current = new Chart(ctx, {
        type: 'line',
        data: data,
        options: {
          scales: {
            x: {
              ticks: {
                autoSkip: true,
                maxTicksLimit: 5,
              },
              title: {
                display: true,
                text: 'Dates'
              }
            },
            y: {
              min: 0,
              max: 10,
              ticks: {
                stepSize: 1
              },
              title: {
                display: true,
                text: 'Business Value'
              }
            }
          }
        }
      });
    }
  }
  else if(parameter === "totalRunningSum"){
    console.log("Graph for Total running sum");
  }
  else if(parameter === "partialRunningSum"){
    console.log("Graph for Partial running sum");
  }
  else{
    console.log("not BV running sum graph");
  }
  }, [projectId, parameter]);
  
  return (
    <Paper sx={{ ...sx, ...styles.container }}>
      {/* { <Typography variant="body2" color="text.secondary">
        Placeholder for Burndown Chart
      </Typography> } */}
       <canvas id="burndownChart"></canvas>
    </Paper>
  );
};

const styles = {
  container: {
    width: '100%',
    maxWidth: '60%', // Set maxWidth to limit the width
    height: '300px',
    background: '#f5f5f5',
    borderRadius: 4,
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    overflow: 'hidden', // Hide any overflow content
  },
};

export default GraphComponent;
