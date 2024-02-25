import { Box, ToggleButton, ToggleButtonGroup } from '@mui/material';
import React, { useState } from 'react';
import PieChartComponent from './PieChartComponent';

const DeliveryOnTime = ({ milestone }) => {
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
			{parameter && <PieChartComponent parameter={parameter} milestoneId={milestone} />}
		</Box>
	);
};

export default DeliveryOnTime;