import React, { useState, useEffect } from 'react';
import { Box, CircularProgress, Typography } from '@mui/material';
import {
	LineChart,
	Line,
	XAxis,
	YAxis,
	Tooltip,
	CartesianGrid,
	Legend,
	Label,
} from 'recharts';

const FoundWorkChart = ({ milestoneId }) => {
	const [foundwork, setFoundwork] = useState([]);
	console.log(milestoneId);
	const [isloading, setIsloading] = useState(true);
	const apiURL = `http://localhost:8080/api/foundWork/${milestoneId}`;
	useEffect(() => {
		(async () => {
			try {
        setIsloading(true);
				const response = await fetch(apiURL);
				if (!response.ok) {
					const errorMessage = await response.text();
					throw new Error(
						`API Request Failed with Status ${response.status}: ${errorMessage}`
					);
				}
				const data = await response.json();
				setFoundwork(data);
				console.log(data);
				setIsloading(false);
			} catch (error) {
				console.error('Error fetching milestone details: ', error.message);
			}
		})();
	}, [milestoneId]);

	const tasksByDate = foundwork?.reduce((acc, task) => {
		const date = new Date(task.createdDate).toDateString();
		acc[date] = (acc[date] || 0) + 1;
		return acc;
	}, {});
	const chartData = Object.entries(tasksByDate).map(([date, count]) => ({
		date,
		count,
	}));

	return (
		<div>
			{isloading && <CircularProgress />}
			{!isloading && (
				<Box width={750} height={300} sx={{ mt: 2 }}>
					<LineChart
						width={730}
						height={250}
						data={chartData}
						margin={{ top: 5, right: 30, left: 20, bottom: 5 }}
					>
						<CartesianGrid strokeDasharray="3 3" />
						<XAxis
							dataKey="date"
							tickFormatter={(dateStr) => {
								const date = new Date(dateStr);
								const month = String(date.getMonth() + 1).padStart(2, '0');
								const day = String(date.getDate()).padStart(2, '0');
								return `${month}/${day}`;
							}}
						></XAxis>
						<YAxis></YAxis>
						<Tooltip />
						<Legend />
						<Line
							type="monotone"
							dataKey="count"
							stroke="#8884d8"
							name="# of tasks added"
						/>
					</LineChart>
				</Box>
			)}
		</div>
	);
};

export default FoundWorkChart;
