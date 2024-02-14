import React, {useState} from 'react'
import { ToggleButton,
ToggleButtonGroup, Box } from '@mui/material'
import GraphComponent from './GraphComponent'

const Burndown = () => {
    const [parameter, setParameter] = useState('')
    return (
        <Box>
            <ToggleButtonGroup
                exclusive
                sx={{ mt: 2 }}
                value={parameter}
                onChange={(event, newParameter) => setParameter(newParameter)}
                >
                <ToggleButton key="patialRunningSum" value="patialRunningSum">
                    Partial Running Sum
                </ToggleButton>
                <ToggleButton key="totalRunningSum" value="totalRunningSum">
                    Total Running Sum
                </ToggleButton>
                <ToggleButton key="BV Running Sum" value="BV Running Sum" disabled>
                    BV Running Sum
                </ToggleButton>
            </ToggleButtonGroup>
            { parameter && <GraphComponent   sx = {{marginY: 2}} parameter={parameter} />}
        </Box>
    )
}

export default Burndown