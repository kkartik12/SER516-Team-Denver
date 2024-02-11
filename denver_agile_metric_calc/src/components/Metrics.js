import React, { useState } from 'react';
import {
  Box,
  List,
  ListItem,
  ListItemText,
  Checkbox,
  ToggleButton,
  ToggleButtonGroup,
  useTheme,
  ListItemButton,
} from '@mui/material';
import ListItemIcon from '@mui/material/ListItemIcon';

const MetricsSection = ({ project }) => {
  return (
    <Box sx={{ display: 'flex', flexDirection: 'row', width: '100%' }}>
        <Box sx={{ flex: 1, padding: 2, overflowY: 'auto', maxHeight: '70vh', borderRight: 1, borderColor: 'divider' }}>
            <h4>Milestones:</h4>
                <List sx={{ width: '100%', maxWidth: 360, bgcolor: 'background.paper' }}>
                    {project.milestones.map((milestone) => (
                        <ListItem key={milestone}>
                            <ListItemButton>
                            <ListItemIcon>
                                <Checkbox edge="start"/>
                            </ListItemIcon>
                            <ListItemText primary={milestone} />
                            </ListItemButton>
                        </ListItem>
                    ))}
                </List>
        </Box>
        <Box sx={{ flex: 3, padding: 2, display: 'flex', flexDirection: 'column' }}>
            <ToggleButtonGroup
            exclusive
            aria-label="metrics-selection"
            sx={{ mt: 2 }}
            >
                <ToggleButton key="Burndown Chart" value="Burndown Chart">
                    Burndown Chart
                </ToggleButton>
                <ToggleButton key="Metric #2" value="Metric #2" disabled>
                    Metric #2 
                </ToggleButton>
                <ToggleButton key="Metric #3" value="Metric #3" disabled>
                    Metric #3 
                </ToggleButton>
                <ToggleButton key="Metric #4" value="Metric #4" disabled>
                    Metric #4 
                </ToggleButton>
            </ToggleButtonGroup>
            <ToggleButtonGroup
            exclusive
            sx={{ mt: 2 }}
            >
                <ToggleButton key="Partial Running Sum" value="Partial Running Sum">
                    Partial Running Sum
                </ToggleButton>
                <ToggleButton key="Total Running Sum" value="Total Running Sum">
                    Total Running Sum
                </ToggleButton>
                <ToggleButton key="BV Running Sum" value="BV Running Sum">
                    BV Running Sum
                </ToggleButton>
            </ToggleButtonGroup>
        </Box>
    </Box>
    );
};

export default MetricsSection;
