import React from 'react'
import { Timeline, TimeAxis, Dot, Tooltip } from 'recharts';

const FoundWorkChart = ({ milestoneId }) => {
  // Filter tasks added after sprint planning (assuming a "plannedDate" prop)
  let tasks
  const postPlanningTasks = tasks.filter(
    (task) => new Date(task.addedDate) > new Date(task.plannedDate)
  );

  // Format task data for Timeline component
  const timelineData = postPlanningTasks.map((task) => ({
    time: task.addedDate,
    color: '#ff9900', // Adjust color as needed
    label: task.name, // Display task name in tooltip
  }));

  return (
    <Box width={750} height={300}>
      <Timeline data={timelineData} syncTooltip>
        <XAxis />
        <YAxis dataKey="time" tickFormatter={(timestamp) => new Date(timestamp).toLocaleDateString('en-US')} />
        <Tooltip />
        <Dot />
      </Timeline>
    </Box>
  );
};

export default FoundWorkChart