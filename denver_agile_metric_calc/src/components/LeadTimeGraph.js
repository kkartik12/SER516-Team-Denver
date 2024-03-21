import {
	Box,
	Card,
	CircularProgress,
	FormControlLabel,
	Grid,
	Switch,
	TextField,
	Typography
} from '@mui/material';
import moment from 'moment';
import React, { useEffect, useState } from 'react';
import {
	CartesianGrid,
	Label,
	Scatter,
	ScatterChart,
	Tooltip,
	XAxis,
	YAxis
} from 'recharts';

const LeadTimeGraph = ({ sx = {}, parameter, milestoneId, createdDate, updatedDate }) => {
	const [milestone, setMilestone] = useState(null);
	const [isLoading, setIsLoading] = useState(true);
	const [error, setError] = useState(null);
	const [isCustomDateRange, setIsCustomDateRange] = useState(false);
	const [startDate, setStartDate] = useState(createdDate);
	const [endDate, setEndDate] = useState(updatedDate);
	const [dateError, setDateError] = useState('');
	console.log("createdDate:", createdDate)
	console.log("updatedDate:", updatedDate)
	useEffect(() => {
		const fetchMilestoneDetails = async () => {
			try {
				setIsLoading(true);
				const response = await fetch(
					`http://localhost:8080/api/leadTime/${parameter}/${milestoneId}`
				);
				if (!response.ok) {
					throw new Error(`API Request Failed with Status ${response.status}`);
				}
				const data = await response.json();
				setMilestone(data);
			} catch (error) {
				setError(error.message);
			} finally {
				setIsLoading(false);
			}
		};

		fetchMilestoneDetails();
	}, []);

	const fetchCustomDetails = async () => {

	}
	const handleCustomDateRangeToggle = (event) => {
		setIsCustomDateRange(event.target.checked);
		if(event.target.checked) {
			fetchCustomDetails()
		}
	};

	const handleStartDateChange = (date) => {
		if (endDate && moment(date).isAfter(endDate)) {
			setDateError('Start date cannot be after end date');
		} else {
			setDateError('');
			setStartDate(date);
		}
	};

	const handleEndDateChange = (date) => {
		if (startDate && moment(date).isBefore(startDate)) {
			setDateError('End date cannot be before start date');
		} else {
			setDateError('');
			setEndDate(date);
		}
	};

	const leadTimeData = milestone?.map((item) => {
		let closedDate;

		if (parameter === 'US') {
			closedDate = item.finishDate;
		} else if (parameter === 'Task') {
			closedDate = item.closedDate;
		}

		return {
			closedDate,
			leadTime: item.leadTime,
		};
	});
	if (leadTimeData) {
		leadTimeData.sort((a, b) => {
			return new Date(a.closedDate) - new Date(b.closedDate);
		});
	}
	const formattedData = leadTimeData?.map((item) => ({
		...item,
		closedDate: moment(item.closedDate).format('DD/MM'),
	}));
	const groupedData = {};

	if (formattedData) {
		formattedData.forEach((item) => {
			const date = item.closedDate;
			if (!groupedData[date]) {
				groupedData[date] = [];
			}
			groupedData[date].push(item.leadTime);
		});
	}
	
	return (
		<div>
			{isLoading && (
				<div style={{ display: 'flex', justifyContent: 'center' }}>
					<CircularProgress />
				</div>
			)}
			{!isLoading && (
				<Card sx={{ width: '100%', height: '100%' }}>
					<Grid container alignItems="center" justifyContent="space-between" sx={{ m: 2, mr: 4 }}>
						<FormControlLabel
							control={<Switch checked={isCustomDateRange} onChange={handleCustomDateRangeToggle} />}
							label="Add Custom Date Range"
						/>
						{isCustomDateRange && (
							<Grid container spacing={2} alignItems="center">
								<Grid item>
									<TextField
										id="startDate"
										label="Start Date"
										type="date"
										value={startDate}
										onChange={(e) => handleStartDateChange(e.target.value)}
										InputLabelProps={{
											shrink: true,
										}}
									/>
								</Grid>
								<Grid item>
									<TextField
										id="endDate"
										label="End Date"
										type="date"
										value={endDate}
										onChange={(e) => handleEndDateChange(e.target.value)}
										InputLabelProps={{
											shrink: true,
										}}
									/>
								</Grid>
								{dateError && (
									<Grid item>
										<Typography variant="body2" color="error">
											{dateError}
										</Typography>
									</Grid>
								)}
							</Grid>
						)}
					</Grid>
					<Box sx={{ display: 'flex' }}>
						<ScatterChart width={500} height={400}>
							<CartesianGrid strokeDasharray="3 3" />
							<XAxis dataKey="closedDate" axisLine={true} tickLine={true}>
								<Label
									value="Closed Date(DD/M)"
									offset={-5}
									position="insideBottom"
								/>
							</XAxis>

							<YAxis dataKey="leadTime" axisLine={true} tickLine={true}>
								<Label
									value="Lead Time (days)"
									offset={10}
									angle={-90}
									position="insideLeft"
								/>
							</YAxis>
							<Tooltip cursor={{ strokeDasharray: '3 3' }} />
							<Scatter data={formattedData} fill="#8884d8" />
						</ScatterChart>
						<Box sx={{ ml: 3 }}>
							<Typography variant="h4">
								Average Lead Time:{' '}
								{Number(
									leadTimeData?.reduce((a, v) => a + v.leadTime, 0) /
										leadTimeData?.length
								).toFixed(2)}{' '}
								days
							</Typography>

							<Typography variant="body1">
								{leadTimeData?.length} items completed
							</Typography>
						</Box>
					</Box>
				</Card>
			)}
		</div>
	);
};

export default LeadTimeGraph;
