package com.example.project;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:config-test.properties")
public class ProjectApplicationTests {

	@InjectMocks
	private ProjectController projectController;

	@Mock
	private ProjectService projectService;

	@Mock
	private HttpServletRequest request;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetProjectList_Successful() {
		// Arrange
		int memberID = 1;
		String token = "mockToken";
		List<ProjectDTO> expectedProjects = new ArrayList<>();
		expectedProjects.add(new ProjectDTO());
		when(projectService.getPojectList(memberID, token)).thenReturn(expectedProjects);

		when(request.getHeader("token")).thenReturn("mockToken");

		// Act
		List<ProjectDTO> projects = projectController.getProjectList(memberID, request);

		// Assert
		assertNotNull(projects);
		assertEquals(expectedProjects.size(), projects.size());
		verify(projectService).getPojectList(memberID,token);
	}

	@Test
	public void testGetProjectList_EmptyList() {
		// Arrange
		int memberID = 1;
		String token = "mockToken";
		when(projectService.getPojectList(memberID, token)).thenReturn(new ArrayList<>());
		when(request.getHeader("token")).thenReturn("mockToken");
		// Act
		List<ProjectDTO> projects = projectController.getProjectList(memberID, request);

		// Assert
		assertNotNull(projects);
		assertEquals(0, projects.size());
		verify(projectService).getPojectList(memberID,token);
	}


}

