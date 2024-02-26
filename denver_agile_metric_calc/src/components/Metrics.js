import {
	Box,
	Checkbox,
	List,
	ListItem,
	ListItemButton,
	ListItemText,
	ToggleButton,
	ToggleButtonGroup,
} from '@mui/material';
import ListItemIcon from '@mui/material/ListItemIcon';
import React, { useState } from 'react';
import Burndown from './Burndown';
import CycleTime from './CycleTime';
import LeadTime from './LeadTime';
import DeliveryOnTime from './DeliveryOnTime';

const MetricsSection = ({ project }) => {
	const [selectedMetric, setSelectedMetric] = useState('');
	const [selectedMilestone, setSelectedMilestone] = useState('');
	const [checked, setChecked] = useState([]);

	const handleToggle = (milestone) => () => {
		setChecked((prevChecked) =>
			prevChecked.includes(milestone)
				? prevChecked.filter((m) => m !== milestone)
				: [...prevChecked, milestone]
		);
		setChecked([]);
		setChecked((prev) => [...prev, milestone]);
		const milestoneId = milestoneDict.find((m) => m.name === milestone)?.id; // Handle potential missing IDs
		if (milestoneId) {
			setSelectedMilestone(milestoneId);
		}
	};
	const handleMetricChange = (event, newMetric) => {
		setSelectedMetric(newMetric);
	};
	const milestoneDict = project.milestones.map((name, index) => ({
		name,
		id: project.milestoneIds[index],
	}));
	return (
		<Box sx={{ display: 'flex', flexDirection: 'row', width: '100%' }}>
			<Box
				sx={{
					flex: 1,
					padding: 2,
					overflowY: 'auto',
					maxHeight: '70vh',
					borderRight: 1,
					borderColor: 'divider',
				}}
			>
				<h4>Milestones:</h4>
				<List
					sx={{ width: '100%', maxWidth: 360, bgcolor: 'background.paper' }}
				>
					{project.milestones.map((milestone) => {
						const labelId = `checkbox-list-label-${milestone}`;
						return (
							<ListItem key={milestone} disablePadding>
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
			<Box
				sx={{ flex: 3, padding: 2, display: 'flex', flexDirection: 'column' }}
			>
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
					<ToggleButton key="Delivery on Time" value="Delivery on Time">
						Delivery on Time
					</ToggleButton>
					<ToggleButton key="Adopted Work" value="Adopted Work">
						Adopted Work
					</ToggleButton>
				</ToggleButtonGroup>
				<React.Fragment>
					{selectedMetric === 'Burndown Chart' && (
						<Burndown milestone={selectedMilestone} />
					)}
					{selectedMetric === 'Cycle Time' && (
						<CycleTime milestone={selectedMilestone} />
					)}
					{selectedMetric === 'Lead Time' && (
						<LeadTime milestone={selectedMilestone} />
					)}
					{selectedMetric === 'Delivery on Time' && (
						<DeliveryOnTime milestone={selectedMilestone} />
					)}
				</React.Fragment>
			</Box>
		</Box>
	);
};

export default MetricsSection;
