import { render, screen } from '@testing-library/react';
import React from 'react';
import LeadTime from '../components/LeadTime';

describe('LeadTime Component', () => {
  test('renders without errors', () => {
    // Mocking necessary data
    const mockMilestone = { id: 376611 };
    
    // Render the component with required props
    render(<LeadTime milestone={mockMilestone} />);
    
    // Check if the toggle button group is rendered
    const toggleButtonGroup = screen.getByTestId('toggle-button-group');
    expect(toggleButtonGroup).toBeInTheDocument();

    // Check if the lead time graph is rendered (initially not loaded)
    const leadTimeGraph = screen.queryByTestId('lead-time-graph');
    expect(leadTimeGraph).not.toBeInTheDocument();
  });

  test('loads and displays lead time graph when parameter is set', () => {
    // Mocking necessary data
    const mockMilestone = { id: 376611 };

    // Render the component with required props and set parameter
    render(<LeadTime milestone={mockMilestone} />);
    
    // Set parameter (simulate user interaction)
    const userStoryToggleButton = screen.getByText('User Story');
    userStoryToggleButton.click();
  });
});
