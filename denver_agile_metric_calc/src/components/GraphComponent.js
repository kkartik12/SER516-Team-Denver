import React from 'react';
import { Box, Paper, Typography } from '@mui/material';

const GraphPlaceholder = ({ sx = {} }) => {
  return (
    <Paper sx={{ ...sx, ...styles.container }}>
      <Typography variant="body2" color="text.secondary">
        Placeholder for Burndown Chart
      </Typography>
    </Paper>
  );
};

const styles = {
  container: {
    width: '100%',
    height: '300px',
    background: '#f5f5f5',
    borderRadius: 4,
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
  },
};

export default GraphPlaceholder;
