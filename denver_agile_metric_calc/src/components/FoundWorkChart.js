import React, { useState, useEffect } from 'react';
import { Box, CircularProgress } from '@mui/material';
import { Typography } from '@mui/material';
import { Timeline, 
  TimelineItem, 
  TimelineSeparator, 
  TimelineConnector, 
  TimelineContent, 
 } from '@mui/lab';


const FoundWorkChart = ({ milestoneId }) => {
  
  const  [foundwork, setFoundwork] = useState([])
  console.log(milestoneId)
  const [isloading, setIsloading] = useState(true)
	const apiURL = `http://localhost:8080/api/foundWork/${milestoneId}`;
	useEffect(() => {
		(async () => {
			try {
				const response = await fetch(apiURL);
				if (!response.ok) {
					const errorMessage = await response.text();
					throw new Error(
						`API Request Failed with Status ${response.status}: ${errorMessage}`
					);
				}
				const data = await response.json();
				setFoundwork(data);
        console.log(data)
        setIsloading(false)
			} catch (error) {
				console.error('Error fetching milestone details: ', error.message);
			}
		})();
	}, [milestoneId]);

  return (
    <Box width={750} height={300}>
     {/*  <Box>
        {isloading && <CircularProgress />}
      </Box>
      {!isloading && (
        <Timeline>
        {postPlanningTasks.map((task) => (
          <TimelineItem key={task.id}>
            <TimelineSeparator>
              <TimelineDot color="primary" />
              <TimelineConnector />
            </TimelineSeparator>
            <TimelineContent>
              <Typography variant="body2" color="text.secondary">
                {new Date(task.addedDate).toLocaleDateString('en-US')}
              </Typography>
              <Typography variant="body1">{task.name}</Typography>
            </TimelineContent>
          </TimelineItem>
        ))}
      </Timeline>
      )} */}
    </Box>
  );
};

const TimelineDot = ({ color }) => (
  <span
    style={{
      display: 'inline-block',
      height: '12px',
      width: '12px',
      borderRadius: '50%',
      backgroundColor: color || 'primary.main',
    }}
  />
);
export default FoundWorkChart