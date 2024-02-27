import { render, screen, waitFor } from '@testing-library/react';
import AdoptedWorkComponent from '../components/AdoptedWorkComponent';

describe('AdoptedWork Component', () => {
    it('renders loading spinner initially', () => {
        render(<AdoptedWorkComponent projectID={123} />);
        const loadingSpinner = screen.getByRole('progressbar');
        expect(loadingSpinner).toBeInTheDocument();
    });

    it('renders individual bars in the bar chart after loading', async () => {
        jest.spyOn(global, 'fetch').mockResolvedValueOnce({
            ok: true,
            json: () => Promise.resolve([
                { name: 'Sprint 1', adoptedWork: 10, sprintTotalPoints: 20 },
                { name: 'Sprint 2', adoptedWork: 15, sprintTotalPoints: 25 },
            ]),
        });

        render(<AdoptedWorkComponent projectID={123} />);

        // Wait for loading to finish
        await waitFor(() => {
            const loadingSpinner = screen.queryByRole('progressbar');
            expect(loadingSpinner).not.toBeInTheDocument();
        });

        // Check if bar chart is rendered properly
        const bar1 = screen.getByText('Sprint 1');
        const bar2 = screen.getByText('Sprint 2');
        expect(bar1).toBeInTheDocument();
        expect(bar2).toBeInTheDocument();
    });
});
