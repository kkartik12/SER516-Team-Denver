import React, {useState} from 'react'
import { ToggleButton,
ToggleButtonGroup, Box } from '@mui/material'
import GraphComponent from './GraphComponent'

const Burndown = ({ milestones }) => {
    const [parameter, setParameter] = useState('')
    const [parameters, setParameters] = useState([])
    const handleParameterChange = (event, newParameter) => {
        setParameters(newParameter)
    }
    console.log(parameters)
    return (
        <Box>
            <ToggleButtonGroup
                exclusive={false}
                multiple 
                sx={{ mt: 2 }}
                value={parameters}
                onChange={handleParameterChange}
                >
                <ToggleButton key="partialRunningSum" value="partialRunningSum">
                    Partial Running Sum
                </ToggleButton>
                <ToggleButton key="totalRunningSum" value="totalRunningSum">
                    Total Running Sum
                </ToggleButton>
                <ToggleButton key="businessValue" value="businessValue">
                    BV Running Sum
                </ToggleButton>
            </ToggleButtonGroup>
            { parameters && <GraphComponent  sx = {{marginY: 2}} parameters={parameters} milestoneIds = {milestones}/>}
        </Box>
    )
}

export default Burndown