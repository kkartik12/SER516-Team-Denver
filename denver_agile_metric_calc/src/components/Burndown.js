import React, {useState} from 'react'
import { ToggleButton,
ToggleButtonGroup } from '@mui/material'

const Burndown = () => {
    const [paramter, setParameter] = useState('')
    return (
        <ToggleButtonGroup
                exclusive
                sx={{ mt: 2 }}
                value={paramter}
                onChange={(event, newParameter) => setParameter(newParameter)}
                >
                <ToggleButton key="Partial Running Sum" value="Partial Running Sum">
                    Partial Running Sum
                </ToggleButton>
                <ToggleButton key="Total Running Sum" value="Total Running Sum">
                    Total Running Sum
                </ToggleButton>
                <ToggleButton key="BV Running Sum" value="BV Running Sum">
                    BV Running Sum
                </ToggleButton>
            </ToggleButtonGroup>
    )
}

export default Burndown