import { CircularProgress } from '@mui/material';
import React, { PureComponent, useEffect, useState } from 'react';
import {
	Bar,
	BarChart,
	CartesianGrid,
	XAxis,
	YAxis,
	Legend,
	Tooltip,
} from 'recharts';

const AdoptedWork = ({ projectID }) => {
	const [adoptedWork, setAdoptedWork] = useState([]);
	const [loading, setLoading] = useState(true);

	const apiURL = `http://localhost:8080/api/adoptedWork/project/${projectID}`;
	useEffect(() => {
		(async () => {
			try {
				const response = await fetch(apiURL);
				if (!response.ok) {
					const errorMessage = await response.text();
					throw new Error(
						`API Request Failed with Status ${response.status}: ${errorMessage}`
					);
				}
				const data = await response.json();
				data.forEach((element) => {
					element['Adopted Work'] = element.adoptedWork;
					element['Total Points'] = element.sprintTotalPoints;
					element.name = element.milestoneName;
				});
				setAdoptedWork(data);
				setLoading(false);
			} catch (error) {
				setLoading(false);
				console.error('Error fetching milestone details: ', error.message);
			}
		})();
	}, [projectID]);

	return (
		<div>
			<div style={{ display: 'flex', justifyContent: 'center' }}>
				{loading && <CircularProgress />}
			</div>
			{!loading && (
				<BarChart
					layout="horizontal"
					width={500}
					height={500}
					data={adoptedWork}
					margin={{
						top: 30,
						right: 20,
						bottom: 10,
						left: 10,
					}}
				>
					<CartesianGrid stroke="#f5f5f5" />
					<YAxis type="number" />
					<XAxis dataKey="name" type="category" />
					<Tooltip />
					<Legend />
					<Bar dataKey="Adopted Work" stackId="a" fill="#8884d8" />
					<Bar dataKey="Total Points" stackId="a" fill="#82ca9d" />
				</BarChart>
			)}
		</div>
	);
};
export default AdoptedWork;
