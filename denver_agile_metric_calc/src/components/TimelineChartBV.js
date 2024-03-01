import {
    Alert,
    AlertTitle,
    Box,
    CircularProgress,
    Divider,
    FormControl,
    FormControlLabel,
    FormLabel,
    Radio,
    RadioGroup,
    Typography,
} from "@mui/material";
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Bar, BarChart, CartesianGrid, Label, Legend, Tooltip, XAxis, YAxis } from 'recharts';


const TimelineChart = () => {
    const { projectId , slug} = useParams();
    const [project, setProject] = useState(null);
    const [errorDialogOpen, setErrorDialogOpen] = useState(false);
    let apiURL;
	if (projectId) {
		apiURL = `http://localhost:8080/api/DoT/${projectId}/BV`; 
	} else if (slug) {
		apiURL = `http://localhost:8080/api/DoT/by-slug/${slug}/BV`; 
	}
    const [currentPage, setCurrentPage] = useState(1);
    const pageSize = 10; 
    useEffect(() => {
		const fetchProjectDetails = async () => {
			try {
				const response = await fetch(apiURL);
				if (response.ok) {
					const data = await response.json();
					setProject(data)
				} else {
					throw new Error('Failed to fetch project details');
				}
				} catch (error) {
					console.error('Error fetching project details:', error);
                    setErrorDialogOpen(true);
				} 
			};
	
			fetchProjectDetails();
		}, [projectId]);
    if (!project) {
            return <CircularProgress />;
    }
    
    if( project[0]?.bvCompleted === null && project[0]?.bvTotal === null){
        return <Alert severity="error">
                <AlertTitle>Error</AlertTitle>
                    This project does not have a business value attribute.</Alert>
    }
    const paginatedData = project.slice((currentPage - 1) * pageSize, currentPage * pageSize);
    const handlePageChange = (event) => {
        setCurrentPage(parseInt(event.target.value));
    };
    return (
        <Box>
        <Typography variant="h4" gutterBottom>
            Sprint-wise Breakdown:
        </Typography>
        <Box display="flex" flexDirection="row" mt={2}>
            <Box>
            <BarChart data={paginatedData} width={750} height={500}>
            <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="milestoneName">
                </XAxis>
                <YAxis tickcount={5}>
                <Label value = "Points" position="insideLeft" offset={10} angle={-90}/>
                </YAxis>
                <Tooltip />  
                <Legend />
                <Bar dataKey="bvCompleted" fill="#8884d8" name="Completed Points" stackId="a"/>
                <Bar dataKey="bvTotal" fill="#82ca9d" name="Planned Points" stackId="a"/>
            </BarChart>
            </Box>
            <Divider orientation="vertical" />
            <Box>
            <FormControl sx={{ml: 2}}>
                <FormLabel>Sprint Range:</FormLabel>
                <RadioGroup sx={{ 
                display: 'flex', 
                flexDirection: 'column', 
                alignItems: 'center',
                }}>
                {[...Array(Math.ceil(project.length / pageSize))].map((_, index) => (
                    <FormControlLabel
                    key={index + 1}
                    value={index + 1}
                    control={<Radio checked={currentPage === index + 1} />}
                    label={`${index * pageSize + 1}-${(index + 1) * pageSize}`}
                    onChange={handlePageChange}
                    />
                ))}
                </RadioGroup>
            </FormControl>
            </Box>
        </Box>
        </Box>
    );
};

export default TimelineChart;
/* 
<Alert severity="error">
          <AlertTitle>Error</AlertTitle>
          This project does not have a business value attribute.
        </Alert> */