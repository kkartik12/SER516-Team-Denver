import { Box, Card, CircularProgress, FormControlLabel, Grid, Switch, TextField, Typography } from '@mui/material';
import moment from 'moment';
import React, { useEffect, useState } from 'react';
import { CartesianGrid, Label, Scatter, ScatterChart, Tooltip, XAxis, YAxis } from 'recharts';

const CycleTimeGraph = ({ sx = {}, parameter, milestoneId, createdDate, updatedDate, projectId }) => {
    const [milestone, setMilestone] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState(null);
    const [isCustomDateRange, setIsCustomDateRange] = useState(false);
    const [startDate, setStartDate] = useState(createdDate);
    const [endDate, setEndDate] = useState(updatedDate);
    const [dateError, setDateError] = useState('');

    useEffect(() => {
        const fetchMilestoneDetails = async () => {
            try {
                setIsLoading(true);
                let url = isCustomDateRange ? `http://localhost:8080/api/cycleTime/${parameter}/byTime/${projectId}?startDate=${startDate}&endDate=${endDate}` :`http://localhost:8080/api/cycleTime/${parameter}/${milestoneId}?startDate=${startDate}&endDate=${endDate}`
                const response = await fetch(url);
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
        const listener = () => {
			fetchMilestoneDetails();
		};
    }, [isCustomDateRange, startDate, endDate, parameter, milestoneId]);

    const handleCustomDateRangeToggle = () => {
        setIsCustomDateRange(!isCustomDateRange);
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

    const cycleTimeData = milestone?.map((item) => {
        let closedDate;
		if (parameter === 'US') {
			closedDate = item.finishDate;
		} else if (parameter === 'Task') {
			closedDate = item.closedDate;
		}
		return {
			closedDate,
			cycleTime: item.cycleTime,
		};
    });

    if (cycleTimeData) {
        cycleTimeData.sort((a, b) => new Date(a.closedDate) - new Date(b.closedDate));
    }

    const formattedData = cycleTimeData?.map((item) => ({
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
            groupedData[date].push(item.cycleTime);
        });
    }

    return (
        <div>
            <div style={{ display: 'flex', justifyContent: 'center' }}>
                {isLoading && <CircularProgress />}
            </div>
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

                            <YAxis dataKey="cycleTime" axisLine={true} tickLine={true}>
                                <Label
                                    value="Cycle Time (days)"
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
                                Average Cycle Time:{' '}
                                {Number(
                                    cycleTimeData?.reduce((a, v) => a + v.cycleTime, 0) /
                                        cycleTimeData?.length
                                ).toFixed(2)}{' '}
                                days
                            </Typography>

                            <Typography variant="body1">
                                {cycleTimeData?.length} items completed
                            </Typography>
                        </Box>
                    </Box>
                </Card>
            )}
        </div>
    );
};

export default CycleTimeGraph;
