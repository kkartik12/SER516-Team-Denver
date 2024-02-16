import React, { useEffect, useState, useRef } from 'react';
import { Box, Paper, Typography } from '@mui/material';
import { useParams } from 'react-router-dom';
import Chart from 'chart.js/auto';

const GraphComponent = ({ sx = {} , parameter, milestoneId}) => {
  const { projectId } =  useParams()
  const [milestone, setMilestone] = useState({})
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
  }, [parameter, milestoneId])
}

export default GraphComponent;
