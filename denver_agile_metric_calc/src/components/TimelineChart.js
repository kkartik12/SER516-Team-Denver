import React, { useState } from 'react';
import { BarChart, XAxis, YAxis, Tooltip, Legend, Bar, CartesianGrid, Label } from 'recharts';
import {RadioGroup , FormControlLabel, Radio, Box, FormControl, FormLabel, Divider, Card, Typography, Alert, AlertTitle}  from "@mui/material";


const TimelineChart = ({ milestones, parameter }) => {
  const [currentPage, setCurrentPage] = useState(1);
  const pageSize = 10; 
  const completedPointsKey = parameter === 'SP' ? 'spCompleted' : 'bvCompleted';
  const totalPointsKey = parameter === 'SP' ? 'totalPoints' : 'bvTotal';
  
  const hasBusinessValue = milestones.every(
    (milestone) => milestone[parameter === 'SP' ? 'totalPoints' : 'bvTotal'] !== null
  );

  const paginatedData = milestones.slice((currentPage - 1) * pageSize, currentPage * pageSize);

  const handlePageChange = (event) => {
    setCurrentPage(parseInt(event.target.value));
  };

  return (
    <Box>
      {!hasBusinessValue && ( 
        <Alert severity="error">
          <AlertTitle>Error</AlertTitle>
          This project does not have a business value attribute.
        </Alert>
      )}
      {hasBusinessValue && ( 
<>
  <Typography variant="h4" gutterBottom>
    Sprint-wise Breakdown:
  </Typography>
  <Box display="flex" flexDirection="row" mt={2}>
    <Box>
      <BarChart data={paginatedData} width={750} height={500}>
      <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="milestoneName">
          </XAxis>
          <YAxis tickcount={5}>
          <Label value = "Points" position="insideLeft" offset={10} angle={-90}/>
          </YAxis>
          <Tooltip />  
          <Legend />
          <Bar dataKey={completedPointsKey} fill="#8884d8" name="Completed Points" />
          <Bar dataKey={totalPointsKey} fill="#82ca9d" name="Planned Points"/>
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
</>
)}
</Box>
  );
};

export default TimelineChart;

