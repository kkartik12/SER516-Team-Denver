import React, { useState } from 'react';
import {
  Box,
  List,
  ListItem,
  ListItemText,
  Checkbox,
  ToggleButton,
  ToggleButtonGroup,
  ListItemButton,
} from '@mui/material';
import ListItemIcon from '@mui/material/ListItemIcon';
import Burndown from './Burndown';

const MetricsSection = ({ project }) => {
    const [selectedMetric, setSelectedMetric]  = useState('');
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
                value={selectedMetric}
                onChange={(event, newMetric) => setSelectedMetric(newMetric)}
                aria-label="metrics-selection"
                sx={{ mt: 2 }}
            >
                <ToggleButton key="Burndown Chart" value="Burndown Chart">
                Burndown Chart
                </ToggleButton>
                <ToggleButton key="Cycle Time" value="Cycle Time">
                    Cycle Time
                </ToggleButton>
                <ToggleButton key="Lead Time" value="Lead Time">
                    Lead Time
                </ToggleButton>                
            </ToggleButtonGroup>
            {selectedMetric === 'Burndown Chart' && <Burndown />}
        </Box>
    </Box>
    );
};

export default MetricsSection;
