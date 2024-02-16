import React, { useEffect, useState, useRef } from 'react';
import { Box, Paper, Typography } from '@mui/material';
import { useParams } from 'react-router-dom';
import Chart from 'chart.js/auto';

const GraphComponent = ({ sx = {} , parameter, milestoneId}) => {
  const { projectId } =  useParams()
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
    fetchMilestoneDetails();
  }, [parameter, milestoneId])

  useEffect(() => {
  if(parameter === "businessValue" && milestone.totalSumValue){
    console.log(milestone)
    const uniqueDatesMap = new Map();
    milestone.totalSumValue.forEach(entry => {
      const currentValue = uniqueDatesMap.get(entry.date);
      if (currentValue === undefined || entry.value > currentValue) {
        uniqueDatesMap.set(entry.date, entry.value);
      }
    });
    const labels = Array.from(uniqueDatesMap.keys());
    const values = Array.from(uniqueDatesMap.values());
    const graphData = {
      labels: labels,
      datasets: [{
        label: 'Burndown Chart BV',
        data: values,
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
        data: graphData,
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
              max: 20,
              ticks: {
                stepSize: 2
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
  }, [parameter, milestone.totalSumValue]);
  
  return (
    <Paper sx={{ ...sx, ...styles.container }}>
      <canvas id="burndownChart"></canvas>
    </Paper>
  );
};

const styles = {
  container: {
    width: '100%',
    maxWidth: '60%', 
    height: '300px',
    background: '#f5f5f5',
    borderRadius: 4,
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    overflow: 'hidden', 
  },
};

export default GraphComponent;
