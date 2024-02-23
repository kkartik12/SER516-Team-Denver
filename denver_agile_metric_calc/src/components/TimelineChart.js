import React from "react";
import {LineChart, XAxis, YAxis} from 'recharts'

const TimelineChart = ({ parameter }) => {
    return (
        <LineChart>
            <XAxis dataKey="sprint" />
            <YAxis />
        </LineChart>
    )
}

export default TimelineChart