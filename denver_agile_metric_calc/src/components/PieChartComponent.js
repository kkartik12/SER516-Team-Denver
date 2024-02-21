import React  from 'react';
import { Pie, PieChart, Tooltip } from 'recharts';

const PieChartComponent = ({ parameter }) => {
	const data = [
		{
			name: 'Group A',
			value: 400,
		},
		{
			name: 'Group B',
			value: 300,
		},
		{
			name: 'Group C',
			value: 300,
		},
		{
			name: 'Group D',
			value: 200,
		},
		{
			name: 'Group E',
			value: 278,
		},
		{
			name: 'Group F',
			value: 189,
		},
	];
	return (
		<PieChart width={500} height={300}>
			<Pie
				data={data}
				dataKey="value"
				nameKey="name"
				cx="50%"
				cy="50%"
				innerRadius={60}
				outerRadius={80}
				fill="#568c8c"
				label
			/>
			<Tooltip />
		</PieChart>
	);
};

export default PieChartComponent;
