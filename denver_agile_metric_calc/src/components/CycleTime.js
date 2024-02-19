import {
  Box,
  ToggleButton,
  ToggleButtonGroup
} from '@mui/material'
import React, { useState } from 'react'

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
      </Box>
  )
}

export default CycleTime

