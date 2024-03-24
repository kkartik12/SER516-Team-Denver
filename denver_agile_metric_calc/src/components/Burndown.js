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
    return (
        <Box>
            <ToggleButtonGroup
                exclusive={false}
                multiple 
                sx={{ mt: 2 }}
                value={parameters}
                onChange={handleParameterChange}
                >
                <ToggleButton key="partialSum" value="partialSum">
                    Partial Running Sum
                </ToggleButton>
                <ToggleButton key="totalSum" value="totalSum">
                    Total Running Sum
                </ToggleButton>
                <ToggleButton key="BVSum" value="BVSum">
                    BV Running Sum
                </ToggleButton>
            </ToggleButtonGroup>
            { parameters && <GraphComponent  sx = {{marginY: 2}} parameters={parameters} milestoneIds = {milestones}/>}
        </Box>
    )
}

export default Burndown