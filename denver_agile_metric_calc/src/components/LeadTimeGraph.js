import React, { useState, useEffect } from 'react';
import { Card, CardContent, CardMedia, Box, Typography } from '@mui/material';
import moment from 'moment';
import { ScatterChart, 
  Scatter, 
  BarChart, 
  XAxis, 
  XAxisProps, 
  YAxis, 
  YAxisProps, 
  Tooltip, 
  Text, 
  Label, 
  CartesianGrid} from 'recharts';

// Ensure you also import the necessary charting library components (e.g., BarChart, Scatter)

const LeadTimeGraph = ({ sx = {}, parameter, milestoneId }) => {
  const [milestone, setMilestone] = useState(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);


  useEffect(() => {
    const fetchMilestoneDetails = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/leadTime/${parameter}/${milestoneId}`);
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

  const leadTimeData = milestone?.map((item) => ({ 
    closedDate: moment(item.finishDate).format('DD/MM'),
    leadTime: item.leadTime, 
  }));

  const groupedData = {};
  
  if(leadTimeData) {
    leadTimeData.forEach(item => {
      const date = item.closedDate;
      if(!groupedData[date]) {
        groupedData[date] = [];
      }
      groupedData[date].push(item.leadTime); 
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
          dataKey="leadTime"
          axisLine={true}
          tickLine={true}  
        >
          <Label value="Lead Time (days)" offset={10} angle={-90} position="insideLeft" />
        </YAxis>  
          <Tooltip cursor={{ strokeDasharray: '3 3' }}/>
          <Scatter 
            data={leadTimeData}
            fill="#8884d8"
          />

          </ScatterChart>
          <Box sx={{ ml: 3 }}>
            <Typography variant="h4">
            Average Lead Time: {Number(leadTimeData?.reduce((a, v) => a + v.leadTime, 0) / leadTimeData?.length).toFixed(2)} days
            </Typography>

            <Typography variant="body1">
              {leadTimeData?.length} items completed
            </Typography>
          </Box>
        </Box>
    </Card>
  );
};

export default LeadTimeGraph;
