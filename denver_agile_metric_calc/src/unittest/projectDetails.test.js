import React from 'react';
import { BrowserRouter } from 'react-router-dom';
import { render } from '@testing-library/react';
import ProjectDetails from '../routes/ProjectDetails';

test('renders project details page without crashing', () => {
  const mockParams = { projectId: '1522293' };
  jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useParams: () => mockParams,
  }));

  render(
    <BrowserRouter>
      <ProjectDetails />
    </BrowserRouter>
  );
});
