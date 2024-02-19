import {
  Box,
  ToggleButton,
  ToggleButtonGroup
} from '@mui/material'
import React, { useState } from 'react'
import CycleTimeGraph from './CycleTimeGraph'

const CycleTime = ({ milestone }) => {
  const [parameter, setParameter] = useState('')
  return (
      <Box>
          <ToggleButtonGroup
              data-testid="toggle-button-group"
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
          { parameter && <CycleTimeGraph  sx = {{marginY: 2}} parameter={parameter} milestoneId = {milestone}/>}
      </Box>
  )
}

export default CycleTime

