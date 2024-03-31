import { render, screen } from '@testing-library/react';
import AdoptedWorkComponent from '../components/AdoptedWorkComponent';

describe('AdoptedWork Component', () => {
  it('renders loading spinner initially', () => {
    render(<AdoptedWorkComponent projectID={1521717} />);
    const loadingSpinner = screen.getByRole('progressbar');
    expect(loadingSpinner).toBeInTheDocument();
  });
});