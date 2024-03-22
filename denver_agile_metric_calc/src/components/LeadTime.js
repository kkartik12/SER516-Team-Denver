import React, {useState, useEffect} from 'react'
import { ToggleButton,
ToggleButtonGroup, 
Box, 
Switch,
FormGroup,
FormControlLabel } from '@mui/material'
import LeadTimeGraph from './LeadTimeGraph'


const LeadTime = ({ milestone, createdDate, updatedDate, projectId }) => {
    const [parameter, setParameter] = useState('')
    const [dates, setDates] = useState(null)
    const [isLoading, setIsLoading] = useState(true);
	const [error, setError] = useState(null);
    return (
        <Box>
            <ToggleButtonGroup
                exclusive
                sx={{ mt: 2 }}
                value={parameter}
                onChange={(event, newParameter) => setParameter(newParameter)}
                >
                <ToggleButton key="US" value="US">
                    User Story
                </ToggleButton>
                <ToggleButton key="Task" value="Task">
                    Task
                </ToggleButton>
            </ToggleButtonGroup>

            { parameter && <LeadTimeGraph  
                sx = {{marginY: 2}} 
                parameter={parameter} 
                milestoneId = {milestone}
                createdDate={createdDate}
                updatedDate={updatedDate}
                projectId={projectId}/>}
        </Box>
    )
}

export default LeadTime