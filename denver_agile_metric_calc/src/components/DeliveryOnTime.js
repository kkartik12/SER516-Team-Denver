import { Box, Card, Divider, ToggleButton, ToggleButtonGroup } from '@mui/material';
import React, { useState } from 'react';
import TimelineChart from './TimelineChart';
import TimelineChartBV from './TimelineChartBV';

const DeliveryOnTime = ({ milestones }) => {
	const [parameter, setParameter] = useState('');
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
				{parameter === "SP" && <TimelineChart milestones={milestones} />}
				{parameter === "BV" && <TimelineChartBV />}
			</Card>
		</Box>
	);
};

export default DeliveryOnTime;