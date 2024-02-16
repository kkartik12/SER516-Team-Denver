import React, {useState} from 'react'
import { ToggleButton,
ToggleButtonGroup, Box } from '@mui/material'
import GraphComponent from './GraphComponent'

const Burndown = ({ milestone }) => {
    const [parameter, setParameter] = useState('')
    return (
        <Box>
            <ToggleButtonGroup
                exclusive
                sx={{ mt: 2 }}
                value={parameter}
                onChange={(event, newParameter) => setParameter(newParameter)}
                >
                <ToggleButton key="partialRunningSum" value="partialRunningSum">
                    Partial Running Sum
                </ToggleButton>
                <ToggleButton key="totalRunningSum" value="totalRunningSum">
                    Total Running Sum
                </ToggleButton>
                <ToggleButton key="BusinessValue" value="BusinessValue">
                    BV Running Sum
                </ToggleButton>
            </ToggleButtonGroup>
            { parameter && <GraphComponent  sx = {{marginY: 2}} parameter={parameter} milestoneId = {milestone}/>}
        </Box>
    )
}

export default Burndown