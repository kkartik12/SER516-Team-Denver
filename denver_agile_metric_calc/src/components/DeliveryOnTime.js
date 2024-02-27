import { Box, Card, Divider, ToggleButton, ToggleButtonGroup } from '@mui/material';
import React, { useState } from 'react';
import PieChartComponent from './PieChartComponent';
import TimelineChart from './TimelineChart';

const DeliveryOnTime = ({ milestoneId, milestones }) => {
	const [parameter, setParameter] = useState('');
	console.log(milestones)
	return (
		<Box>
			<ToggleButtonGroup
				exclusive
				sx={{ mt: 2 }}
				value={parameter}
				onChange={(event, newParameter) => setParameter(newParameter)}
			>
				<ToggleButton key="BV" value="BV">
					Business Value
				</ToggleButton>
				<ToggleButton key="SP" value="SP">
					Story Points
				</ToggleButton>
			</ToggleButtonGroup>
			<Card>
				{parameter && <PieChartComponent parameter={parameter} milestoneId={milestoneId} />}
				<Divider variant='middle'/>
				{parameter && <TimelineChart milestones={milestones} parameter={parameter} />}
			</Card>
		</Box>
	);
};

export default DeliveryOnTime;