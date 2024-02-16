import React, {useState} from 'react'
import { ToggleButton,
ToggleButtonGroup, 
Box } from '@mui/material'
import LeadTimeGraph from './LeadTimeGraph'


const LeadTime = ({ milestone }) => {
    const [parameter, setParameter] = useState('')
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
                <ToggleButton key="task" value="task">
                    Task
                </ToggleButton>
            </ToggleButtonGroup>
            { parameter && <LeadTimeGraph  sx = {{marginY: 2}} parameter={parameter} milestoneId = {milestone}/>}
        </Box>
    )
}

export default LeadTime