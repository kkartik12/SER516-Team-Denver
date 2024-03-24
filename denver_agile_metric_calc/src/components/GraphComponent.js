import { CircularProgress, Paper, Card, CardHeader, CardContent, Typography } from '@mui/material';
import React, { useEffect, useRef, useState } from 'react';
import { AreaChart, XAxis, YAxis, Tooltip, Area, CartesianGrid } from 'recharts';

const GraphComponent = ({ sx = {}, parameters, milestoneIds }) => {
	const [isLoading, setIsLoading] = useState(true);
	const [milestones, setMilestones] = useState([])
	const [error, setError] = useState(null)
	console.log(milestoneIds)
	const chartInstance = useRef(null); // Graph instance for all burndown charts
	useEffect(() => {
		const fetchMilestones = async () => {
		  try {
			setIsLoading(true);
			setError(null);
	
			const allPromises = [];
			milestoneIds.forEach((milestoneId) => {
			  parameters.forEach((parameter) => {
				const apiURL = `http://localhost:8080/api/burndownchart/${milestoneId}/${parameter}`;
				allPromises.push(fetch(apiURL));
			  });
			});
	
			const allResponses = await Promise.all(allPromises);
			const parsedMilestones = [];
	
			for (let i = 0; i < milestoneIds.length; i++) {
			  const milestoneData = {
				milestoneID: milestoneIds[i],
			  };
			  for (let j = 0; j < parameters.length; j++) {
				const response = allResponses[i * parameters.length + j];
				if (!response.ok) {
				  throw new Error(`API Request Failed with Status ${response.status}`);
				}
				const parsedData = await response.json();
				milestoneData[parameters[j]] = parsedData[parameters[j]];
			  }
			  parsedMilestones.push(milestoneData);
			}
	
			setMilestones(parsedMilestones);
		  } catch (error) {
			setIsLoading(false);
			setError(error.message);
		  } finally {
			setIsLoading(false);
		  }
		};
	
		fetchMilestones();
	  }, [parameters, milestoneIds]);
	  const getColor = (index) => {
		const colors = ['#cdb4db', '#bde0fe', '#c1121f', '#9467bd', '#e377c2']; 
		return colors[index % colors.length];
	  };
	  return (
		<Card sx={{mt: 2}}>
			{/* <CardHeader title={parameter}/>
			{milestones.map((milestone, index) => (
				<CardContent>	
					<Typography variant="h6" gutterBottom component="div">{milestone.milestoneName}</Typography>
					<AreaChart width={730} height={250} data={parameter == "partialRunningSum" ? milestone.partialSumValue : milestone.totalSumValue}
					margin={{ top: 10, right: 30, left: 0, bottom: 0 }}>
					<defs>
					<linearGradient id={`colorUv-${index}`} x1="0" y1="0" x2="0" y2="1">
						<stop offset="5%" stopColor={getColor(index)} stopOpacity={0.8}/>
						<stop offset="95%" stopColor={getColor(index)} stopOpacity={0}/>
					</linearGradient>
					</defs>
					<XAxis dataKey="date" />
					<YAxis />
					<CartesianGrid strokeDasharray="3 3" />
					<Tooltip />
					<Area type="monotone" dataKey="value" stopColor={getColor(index)} fillOpacity={1} fill={`url(#colorUv-${index})`} />
				</AreaChart>
			  </CardContent>
			))} */}
		</Card>
	  )
};

export default GraphComponent;
