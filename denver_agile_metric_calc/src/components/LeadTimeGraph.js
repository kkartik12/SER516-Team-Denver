import React, {useState, useRef, useEffect} from 'react'
import Chart from 'chart.js/auto';


const LeadTimeGraph = ({sx = {}, parameter, milestoneId}) => {
    const [milestone, setMilestone] = useState({})
    const BvchartInstance = useRef(null); // Graph instance for Business Value
    const apiURL = `http://localhost:8080/api/leadTime/${parameter}/${milestoneId}`
    useEffect(() => {
        const fetchMilestoneDetails = async () => {
            try{
              const response = await fetch(apiURL)
              if(!response.ok) {
                const errorMessage = await response.text()
                throw new Error(`API Request Failed with Status ${response.status}: ${errorMessage}`)
              }
              const data = await response.json()
              console.log("Milestone: ", data)
              setMilestone(data)
            } catch(error) {
              console.error('Error fetching milestone details: ', error.message)
            }
          }
        fetchMilestoneDetails()
    } ,[parameter, milestone]) 
}

export default LeadTimeGraph