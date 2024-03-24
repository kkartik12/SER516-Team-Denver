import { CircularProgress, Paper, Card, CardHeader, CardContent, Typography } from '@mui/material';
import React, { useEffect, useRef, useState } from 'react';
import { AreaChart, XAxis, YAxis, Tooltip, Area, CartesianGrid } from 'recharts';

const GraphComponent = ({ sx = {}, parameters, milestoneIds }) => {
	const [isLoading, setIsLoading] = useState(true);
	const [milestones, setMilestones] = useState([])
	const [error, setError] = useState(null)
	const chartInstance = useRef(null); 
	useEffect(() => {
		const fetchMilestones = async () => {
		  try {
			setIsLoading(true)
			setError(null)
			const allPromises = []
			milestoneIds.forEach((milestoneId) => {
				const apiURL = `http://localhost:8080/api/burndownchart/${milestoneId}?partialSum=${parameters.includes("partialSum")}&totalSum=${parameters.includes("totalSum")}&BVSum=${parameters.includes("BVSum")}`
				console.log(apiURL)
				allPromises.push(fetch(apiURL))
			})
			const allResponses = await Promise.all(allPromises)
			const parsedMilestones = []
			for (let i = 0; i < allResponses.length; i++) {
				const response = allResponses[i]
				if (!response.ok) {
					throw new Error(`API Request Failed with Status ${response.status}`)
				}
				const parsedData = await response.json()
				parsedMilestones.push(parsedData)
			}
        	setMilestones(parsedMilestones)

		  } catch (error) {
			setIsLoading(false)
			setError(error.message)
		  } finally {
			setIsLoading(false);
		  }
		};
	
		fetchMilestones();
	  }, [parameters, milestoneIds]);
	console.log(milestones.length)
	console.log(milestones)
	const getColor = (index) => {
		const colors = ['#cdb4db', '#bde0fe', '#c1121f', '#9467bd', '#e377c2']; 
		return colors[index % colors.length];
	};
	return (
		<Card sx={{ mt: 2 }}>
			{milestones?.map((milestone, index) => (
			<CardContent key={index}>
				<Typography variant="h4" gutterBottom component="div">
				{milestone.milestoneName}
				</Typography>
				{parameters.includes("partialSum") && (
					<div>
						<Typography variant="h6" gutterBottom component="div">
							Partial Running Sum
						</Typography>
						<AreaChart width={730} height={250} data={milestone.partialSumValue}
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
					</div>
					
				)}
				{parameters.includes("totalSum") && (
					<div>
						<Typography variant="h6" gutterBottom component="div">
							Total Running Sum
						</Typography>
						<AreaChart width={730} height={250} data={milestone.totalSumValue}
					margin={{ top: 10, right: 30, left: 0, bottom: 0 }}>
						<defs>
						<linearGradient id={`colorUv-${index}`} x1="0" y1="0" x2="0" y2="1">
							<stop offset="5%" stopColor={getColor(index)} stopOpacity={0.8}/>
							<stop offset="95%" stopColor={getColor(index)} stopOpacity={0}/>
						</linearGradient>
						</defs>
						<XAxis dataKey="date" label="time"/>
						<YAxis />
						<CartesianGrid strokeDasharray="3 3" />
						<Tooltip />
						<Area type="monotone" dataKey="value" stopColor={getColor(index)} fillOpacity={1} fill={`url(#colorUv-${index})`} />
					</AreaChart>
					</div>
					
				)}
				{parameters.includes("BVSum") && (
					<div>
						<Typography variant="h6" gutterBottom component="div">
							BV Running Sum
						</Typography>
						<AreaChart width={730} height={250} data={milestone.totalSumBV}
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
					</div>
					
				)}
			</CardContent>
			))}
	  </Card>
	)
};

export default GraphComponent