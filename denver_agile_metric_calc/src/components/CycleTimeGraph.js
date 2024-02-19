import { Box, Card, Typography } from '@mui/material';
import moment from 'moment';
import React, { useEffect, useState } from 'react';
import {
  CartesianGrid,
  Label,
  Scatter,
  ScatterChart,
  Tooltip,
  XAxis,
  YAxis
} from 'recharts';

const CycleTimeGraph = ({ sx = {}, parameter, milestoneId }) => {
  const [milestone, setMilestone] = useState(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);


  useEffect(() => {
    const fetchMilestoneDetails = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/cycleTime/${parameter}/${milestoneId}`);
        if (!response.ok) {
          throw new Error(`API Request Failed with Status ${response.status}`);
        }
        const data = await response.json();
        setMilestone(data);
      } catch (error) {
        setError(error.message);
      } finally {
        setIsLoading(false);
      }
    };

    fetchMilestoneDetails();
  }, [parameter, milestone]);

  const cycleTimeData = milestone?.map((item) => ({
    closedDate: moment(item.finishDate).format('DD/MM'),
    cycleTime: item.cycleTime,
  }));

  const groupedData = {};
  
  if(cycleTimeData) {
    cycleTimeData.forEach(item => {
      const date = item.closedDate;
      if(!groupedData[date]) {
        groupedData[date] = [];
      }
      groupedData[date].push(item.cycleTime);
    });
  }


 
  return (
    <Card sx={{ width: '100%', height: '100%' }}>
        <Box sx={{ display: 'flex' }}>
          <ScatterChart width={500} height={400}>
          
          <CartesianGrid strokeDasharray="3 3"/>
          <XAxis
          dataKey="closedDate"
          axisLine={true}
          tickLine={true}
        >
          <Label value="Closed Date(DD/M)" offset={-5} position="insideBottom" />
        </XAxis>

        <YAxis
          dataKey="cycleTime"
          axisLine={true}
          tickLine={true}
        >
          <Label value="Cycle Time (days)" offset={10} angle={-90} position="insideLeft" />
        </YAxis>
          <Tooltip cursor={{ strokeDasharray: '3 3' }}/>
          <Scatter
            data={cycleTimeData}
            fill="#8884d8"
          />

          </ScatterChart>
          <Box sx={{ ml: 3 }}>
            <Typography variant="h4">
            Average Cycle Time: {Number(cycleTimeData?.reduce((a, v) => a + v.cycleTime, 0) / cycleTimeData?.length).toFixed(2)} days
            </Typography>

            <Typography variant="body1">
              {cycleTimeData?.length} items completed
            </Typography>
          </Box>
        </Box>
    </Card>
  );
};

export default CycleTimeGraph;
