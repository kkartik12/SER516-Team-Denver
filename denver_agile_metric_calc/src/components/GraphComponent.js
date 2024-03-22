import { CircularProgress, Paper, Card, CardHeader, CardContent, Typography } from '@mui/material';
import React, { useEffect, useRef, useState } from 'react';
import { AreaChart, XAxis, YAxis, Tooltip, Area, CartesianGrid } from 'recharts';

const GraphComponent = ({ sx = {}, parameters, milestoneIds }) => {
	const [isLoading, setIsLoading] = useState(true);
	const [milestones, setMilestones] = useState([])
	const [error, setError] = useState(null)
	console.log(parameters)
	const chartInstance = useRef(null); // Graph instance for all burndown charts
	useEffect(() => {
		const fetchMilestones = async () => {
		  try {
			setIsLoading(true);
			setError(null);
	
			const promises = milestoneIds.map((milestoneId) => {
			  const apiURL = `http://localhost:8080/api/burndownchart/${milestoneId}/${parameter}`;
			  return fetch(apiURL);
			});
	
			const responses = await Promise.all(promises);
			const parsedMilestones = responses.map((response) => {
			  if (!response.ok) {
				throw new Error(`API Request Failed with Status ${response.status}`);
			  }
			  return response.json();
			});
	
			const data = await Promise.all(parsedMilestones);
			setMilestones(data);
	
			if (parameter === 'totalRunningSum') {
			  GraphComponent(data, chartInstance); 
			}
		  } catch (error) {
			setIsLoading(false);
			setError(error.message); 
		  } finally {
			setIsLoading(false);
		  }
		};
	
		fetchMilestones();
	  }, [parameter, milestoneIds]);
	  const getColor = (index) => {
		const colors = ['#cdb4db', '#bde0fe', '#c1121f', '#9467bd', '#e377c2']; 
		return colors[index % colors.length];
	  };
	  return (
		<Card sx={{mt: 2}}>
			<CardHeader title={parameter}/>
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
			))}
		</Card>
	  )
};

export default GraphComponent;
