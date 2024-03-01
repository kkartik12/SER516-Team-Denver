import React, { useState } from 'react';
import { BarChart, XAxis, YAxis, Tooltip, Legend, Bar, CartesianGrid, Label } from 'recharts';
import {
    RadioGroup , 
    FormControlLabel, 
    Radio, 
    Box, 
    FormControl, 
    FormLabel, 
    Divider, 
    Typography
}  from "@mui/material";


const TimelineChart = ({ milestones }) => {
  const [currentPage, setCurrentPage] = useState(1);
  const pageSize = 10; 

  const paginatedData = milestones.slice((currentPage - 1) * pageSize, currentPage * pageSize);
  
  const paginatedDataWithRemainingPoints = paginatedData.map((milestone) => ({
    ...milestone,
    remainingPoints: milestone.totalPoints - milestone.spCompleted,
  }));

  const handlePageChange = (event) => {
    setCurrentPage(parseInt(event.target.value));
  };
  return (
    <Box>
      <Typography variant="h4" gutterBottom>
        Sprint-wise Breakdown:
      </Typography>
      <Box display="flex" flexDirection="row" mt={2}>
        <Box>
          <BarChart data={paginatedDataWithRemainingPoints} width={750} height={500}>
          <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="milestoneName">
              </XAxis>
              <YAxis tickcount={5}>
              <Label value = "Points" position="insideLeft" offset={10} angle={-90}/>
              </YAxis>
              <Tooltip />  
              <Legend />
              <Bar dataKey="spCompleted" fill="#8884d8" name="Completed Points" stackId="a"/>
              <Bar dataKey="remainingPoints" fill="#82ca9d" name="Remaining Points" stackId="a"/>
          </BarChart>
        </Box>
        <Divider orientation="vertical" />
        <Box>
        <FormControl sx={{ml: 2}}>
            <FormLabel>Sprint Range:</FormLabel>
            <RadioGroup sx={{ 
              display: 'flex', 
              flexDirection: 'column', 
              alignItems: 'center',
            }}>
              {[...Array(Math.ceil(milestones.length / pageSize))].map((_, index) => (
                <FormControlLabel
                  key={index + 1}
                  value={index + 1}
                  control={<Radio checked={currentPage === index + 1} />}
                  label={`${index * pageSize + 1}-${(index + 1) * pageSize}`}
                  onChange={handlePageChange}
                />
              ))}
            </RadioGroup>
          </FormControl>
        </Box>
      </Box>
    </Box>
  );
};

export default TimelineChart;

