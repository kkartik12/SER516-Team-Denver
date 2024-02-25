import { default as React, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Legend, Pie, PieChart, Tooltip } from 'recharts';

const PieChartComponent = ({ parameter, milestoneId }) => {
	const [isLoading, setIsLoading] = useState(true)
	const [project, setProject] = useState(null);
	const [errorDialogOpen, setErrorDialogOpen] = useState(false);
	const { projectId , slug} = useParams();
	console.log(projectId)
	let apiURL;
	if (projectId) {
		apiURL = `http://localhost:8080/api/DoT/${projectId}/${parameter}`; // Use projectId if available
	} else if (slug) {
		apiURL = `http://localhost:8080/api/DoT/by-slug/${slug}/${parameter}`; // Use slug if not
	}
	useEffect(() => {
		const fetchProjectDetails = async () => {
			try {
				const response = await fetch(apiURL);
				console.log(response);
				if (response.ok) {
					const data = await response.json();
					console.log(data);
					const filteredData = data.filter(item => item.milestoneID == milestoneId);
					setProject(filteredData);
					console.log(filteredData);
					console.log(filteredData[0]?.bvCompleted);
					console.log(milestoneId);
				} else {
					throw new Error('Failed to fetch project details');
				}
				} catch (error) {
					console.error('Error fetching project details:', error);
					setErrorDialogOpen(true);
				} finally {
					setIsLoading(false);
				}
			};
	
			fetchProjectDetails();
		}, [projectId]);
	
	// Check if project is available and if it is closed
	if (!project) {
		return <div>Loading...</div>;
	}

	const data = [
		{
		name: 'Completed',
		value: project[0]?.bvCompleted,
		fill: '#568c8c',
		},
		{
		name: 'Remaining',
		value: project[0]?.bvTotal - project[0]?.bvCompleted,
		fill: '#0077b3',
		},
	];

	const colors = ['#568c8c', '#ffb366'];

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
				label
			/>
			<Tooltip />
			<Legend
				align="right"
				verticalAlign="middle"
				layout="vertical"
				iconType="circle"
				iconSize={10}
			/>
		</PieChart>
	);
};

export default PieChartComponent;