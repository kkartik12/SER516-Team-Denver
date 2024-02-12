package org.example.JavaTaigaCode.controllers;

import org.example.JavaTaigaCode.models.ProjectDTO;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.example.JavaTaigaCode.service.ProjectService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:config-test.properties")
public class ProjectControllerTest {

    @InjectMocks
    private ProjectController projectController;

    @Mock
    private ProjectService projectService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetProjectList_Successful() {
        // Arrange
        int memberID = 1;
        List<ProjectDTO> expectedProjects = new ArrayList<>();
        expectedProjects.add(new ProjectDTO());
        when(projectService.getPojectList(memberID)).thenReturn(expectedProjects);

        // Act
        List<ProjectDTO> projects = projectController.getProjectList(memberID);

        // Assert
        assertNotNull(projects);
        assertEquals(expectedProjects.size(), projects.size());
        verify(projectService).getPojectList(memberID);
    }

    @Test
    public void testGetProjectList_EmptyList() {
        // Arrange
        int memberID = 1;
        when(projectService.getPojectList(memberID)).thenReturn(new ArrayList<>());

        // Act
        List<ProjectDTO> projects = projectController.getProjectList(memberID);

        // Assert
        assertNotNull(projects);
        assertEquals(0, projects.size());
        verify(projectService).getPojectList(memberID);
    }

    @Test
    public void testGetProjectList_NullList() {
        // Arrange
        int memberID = 1;
        when(projectService.getPojectList(memberID)).thenReturn(null);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            projectController.getProjectList(memberID);
        });
        assertEquals("Unable to get Project List for user", exception.getMessage());
        verify(projectService).getPojectList(memberID);
    }

}
