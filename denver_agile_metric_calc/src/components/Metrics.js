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
} from '@mui/material'
import ListItemIcon from '@mui/material/ListItemIcon'
import Burndown from './Burndown'
import LeadTime from './LeadTime';

const MetricsSection = ({ project }) => {
    const [selectedMetric, setSelectedMetric]  = useState('')
    const [selectedMilestone, setSelectedMilestone] = useState('')
    const [checked, setChecked] = useState([])

    const handleToggle = (milestone) => () => {
        if (checked === milestone) {
            // If the same milestone is already selected, deselect it
            setChecked(null);
            setSelectedMilestone('');
        } else {
            // If a different milestone is selected, update the state
            setChecked(milestone);
            const milestoneId = milestoneDict.find(m => m.name === milestone)?.id; // Handle potential missing IDs
            if (milestoneId) {
              setSelectedMilestone(milestoneId);
            }
        }
    }
    const handleMetricChange = (event, newMetric) => {
        setSelectedMetric(newMetric);
      }
    const milestoneDict = project.milestones.map((name, index) => ({
        name,
        id: project.milestoneIds[index],
      }))
  return (
    <Box sx={{ display: 'flex', flexDirection: 'row', width: '100%' }}>
        <Box sx={{ flex: 1, padding: 2, overflowY: 'auto', maxHeight: '70vh', borderRight: 1, borderColor: 'divider' }}>
            <h4>Milestones:</h4>
            <List sx={{ width: '100%', maxWidth: 360, bgcolor: 'background.paper' }}>
                {project.milestones.map((milestone) => {
                    const labelId = `checkbox-list-label-${milestone}`;
                    return (
                    <ListItem
                        key={milestone}
                        disablePadding
                    >
                        <ListItemButton onClick={handleToggle(milestone)} dense>
                        <ListItemIcon>
                            <Checkbox
                            edge="start"
                            checked={checked.indexOf(milestone) !== -1}
                            tabIndex={-1}
                            inputProps={{ 'aria-labelledby': labelId }}
                            />
                        </ListItemIcon>
                            <ListItemText id={labelId} primary={milestone} />
                        </ListItemButton>
                    </ListItem>
                    );
                })}
            </List>
        </Box>
        <Box sx={{ flex: 3, padding: 2, display: 'flex', flexDirection: 'column' }}>
            <ToggleButtonGroup
                exclusive
                value={selectedMetric}
                onChange={handleMetricChange}
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
            <React.Fragment>
                {selectedMetric === 'Burndown Chart' && (
                    <Burndown milestone={selectedMilestone} />
                )}
                {selectedMetric === 'Lead Time' && (
                    <LeadTime milestone={selectedMilestone} />
                )}
        </React.Fragment>
        </Box>
    </Box>
    );
};

export default MetricsSection
