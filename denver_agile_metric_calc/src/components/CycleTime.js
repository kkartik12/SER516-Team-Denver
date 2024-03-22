import {
  Box,
  ToggleButton,
  ToggleButtonGroup,
  Switch,
  FormGroup,
  FormControlLabel
} from '@mui/material'
import React, { useState, useEffect } from 'react'
import CycleTimeGraph from './CycleTimeGraph'

const CycleTime = ({ milestone, createdDate, updatedDate, projectId }) => {
  const [parameter, setParameter] = useState('')
  const [dates, setDates] = useState(null)
  const [isLoading, setIsLoading] = useState(true);	   
  const [error, setError] = useState(null);
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
          { parameter && <CycleTimeGraph  sx = {{marginY: 2}} parameter={parameter} milestoneId = {milestone} createdDate={createdDate} updatedDate={updatedDate} projectId={projectId}/>}
      </Box>
  )
}

export default CycleTime

