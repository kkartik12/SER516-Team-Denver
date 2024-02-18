import React, { useEffect, useState, useRef } from 'react';
import { Box, Paper, Typography } from '@mui/material';

import Chart from 'chart.js/auto';

const GraphComponent = ({ sx = {} , parameter, milestoneId}) => {
  const [milestone, setMilestone] = useState({})
  const BvchartInstance = useRef(null); // Graph instance for Business Value
  console.log("milestoneId: ", parameter)
  const apiURL = `http://localhost:8080/api/burndownchart/${milestoneId}/${parameter}`

  useEffect(() => {
    const fetchMilestoneDetails = async () => {
      try{
        const response = await fetch(apiURL)
        if(!response.ok) {
          const errorMessage = await response.text()
          throw new Error(`API Request Failed with Status ${response.status}: ${errorMessage}`)
        }
        const data = await response.json()
        console.log("Milestone: ", data)
        setMilestone(data)
      } catch(error) {
        console.error('Error fetching milestone details: ', error.message)
      }
    }
    fetchMilestoneDetails()
      if(parameter === "businessValue"){
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
          
      const ctx = document.getElementById('burndownChart');
      if (BvchartInstance.current) {
        BvchartInstance.current.destroy();
      }    
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
      console.log("no graph");
    }
  }, [parameter, milestoneId])

  return (
    <Paper sx={{ ...sx, ...styles.container }}>
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
