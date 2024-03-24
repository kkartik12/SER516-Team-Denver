package org.example.JavaTaigaCode.service;

import org.example.JavaTaigaCode.models.MilestoneDTO;
import org.example.JavaTaigaCode.models.ProjectDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:config-test.properties")
public class DeliveryOnTimeServiceTest {
    private DeliveryOnTimeService deliveryOnTimeService;

    @Mock
    private ProjectService projectService;

    @Mock
    private BurndownChart burndownChart;

    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        deliveryOnTimeService = mock(DeliveryOnTimeService.class, withSettings().defaultAnswer(CALLS_REAL_METHODS));
        deliveryOnTimeService.projectService = projectService;
        deliveryOnTimeService.burndownChart = burndownChart;
        deliveryOnTimeService.TAIGA_API_ENDPOINT = "http://test.com/api/";
    }

    @Test
    public void testGetClosedMilestonesbyID() {
        // Mock project details
        ProjectDTO projectDTO = new ProjectDTO();
        //projectDTO.setMilestoneIds(List.of("1", "2"));

        // Mock milestone details
        MilestoneDTO milestoneDTO1 = new MilestoneDTO();
        milestoneDTO1.setMilestoneID(1);
        milestoneDTO1.setMilestoneName("Milestone 1");

        MilestoneDTO milestoneDTO2 = new MilestoneDTO();
        milestoneDTO2.setMilestoneID(2);
        milestoneDTO2.setMilestoneName("Milestone 2");

        projectDTO.setMilestoneDetails(List.of(milestoneDTO1, milestoneDTO2));
        when(projectService.getPojectDetails(anyInt())).thenReturn(projectDTO);
        when(deliveryOnTimeService.getMilestondeDetails(1)).thenReturn(milestoneDTO1);
        when(deliveryOnTimeService.getMilestondeDetails(2)).thenReturn(milestoneDTO2);

        List<MilestoneDTO> result = deliveryOnTimeService.getClosedMilestonesbyID(123);

        assertEquals(2, result.size());
        assertEquals("Milestone 1", result.get(0).getMilestoneName());
        assertEquals("Milestone 2", result.get(1).getMilestoneName());

        // Verify that projectService.getPojectDetails is called once
        verify(projectService, times(1)).getPojectDetails(123);
        // Verify that getMilestondeDetails is called twice with correct milestone IDs
        verify(deliveryOnTimeService, times(1)).getMilestondeDetails(1);
        verify(deliveryOnTimeService, times(1)).getMilestondeDetails(2);
    }

}
