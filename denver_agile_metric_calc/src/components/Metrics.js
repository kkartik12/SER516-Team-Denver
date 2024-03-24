import {
	Box,
	List,
	ListItem,
	ListItemButton,
	ListItemText,
	ToggleButton,
	ToggleButtonGroup,
	Checkbox
} from '@mui/material';
import ListItemIcon from '@mui/material/ListItemIcon';
import React, { useState } from 'react';
import Burndown from './Burndown';
import CycleTime from './CycleTime';
import DeliveryOnTime from './DeliveryOnTime';
import LeadTime from './LeadTime';
import AdoptedWork from './AdoptedWorkComponent';
import FoundWorkChart from './FoundWorkChart';


const MetricsSection = ({ project }) => {
	const [selectedMetric, setSelectedMetric] = useState('')
	const [selectedMilestone, setSelectedMilestone] = useState('')
	const [checked, setChecked] = useState({})
	const [milestones, setMilestones] = useState([])
	const handleToggle = (milestone) => () => {
		setChecked((prevChecked) => ({
		  ...prevChecked, // Keep existing key-value pairs
		  [milestone]: !prevChecked[milestone] || false, // Toggle selection for the clicked milestone, set default to false
		}));
		const milestoneId = milestoneDict.find((m) => m.name === milestone)?.id; // Handle potential missing IDs
		if (milestoneId) {
			setSelectedMilestone(milestoneId)
			const isSelected = !checked[milestone] || false; // Get selection state
		
			setMilestones((prevMilestones) => {
			  // Handle potential undefined state of prevMilestones
			  return prevMilestones ? [...new Set([...prevMilestones, milestoneId])] : [milestoneId];
			});
		
			if (!isSelected) {
			  // Deselection: Filter out only if milestones exist (prevents errors)
			  setMilestones((prevMilestones) =>
				prevMilestones?.filter((id) => id !== milestoneId)
			  );
			}
		  }
	}
	const handleMetricChange = (event, newMetric) => {
		setSelectedMetric(newMetric);
	};
	const milestoneDict = project.milestones.map((name, index) => ({
		name,
		id: project.milestoneDetails[index].milestoneID,
		isClosed: project.milestoneDetails[index].isClosed,
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
				{/* <List
					sx={{ width: '100%', maxWidth: 360, bgcolor: 'background.paper' }}
				>
					{project.milestones.map((milestone) => {
						const labelId = `radio-list-label-${milestone}`; // Change the label ID
						return (
							<ListItem key={milestone} disablePadding>
								<ListItemButton onClick={handleToggle(milestone)} dense>
									<ListItemIcon>
										<Radio
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
				</List> */}
				<List sx={{ width: '100%', maxWidth: 360, bgcolor: 'background.paper' }}>
				{project.milestones.map((milestone) => {
					const labelId = `checkbox-label-${milestone}`; // Change the label ID
					return (
					<ListItem key={milestone} disablePadding dense>
						<ListItemButton onClick={handleToggle(milestone)}>
						<ListItemIcon>
							<Checkbox
							edge="start"
							checked={checked[milestone] || false} // Set default to false
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
					<ToggleButton key="Found Work" value="Found Work">
						Found Work
					</ToggleButton>
				</ToggleButtonGroup>
				<React.Fragment>
					{selectedMetric === 'Burndown Chart' && (
						<Burndown milestones={milestones}/>
					)}
					{selectedMetric === 'Cycle Time' && (
						<CycleTime milestone={selectedMilestone} createdDate={project.createdDate} updatedDate={project.updatedDate} projectId={project.projectID}/>
					)}
					{selectedMetric === 'Lead Time' && (
						<LeadTime milestone={selectedMilestone} 
						createdDate={project.createdDate}
						updatedDate={project.updatedDate}
						projectId={project.projectID}/>
					)}
					{selectedMetric === 'Delivery on Time' && (
						<DeliveryOnTime milestones={project.milestoneDetails}
						/>
					)}
					{selectedMetric === 'Adopted Work' && (
						<AdoptedWork projectID={project.projectID} />
					)}
					{selectedMetric === 'Found Work' && (
						<FoundWorkChart milestoneId={selectedMilestone} />
					)}
				</React.Fragment>
			</Box>
		</Box>
	);
};

export default MetricsSection;
