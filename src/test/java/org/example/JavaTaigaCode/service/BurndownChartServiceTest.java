package org.example.JavaTaigaCode.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicStatusLine;
import org.example.JavaTaigaCode.models.BurndownChartDTO;
import org.example.JavaTaigaCode.models.MilestoneDTO;
import org.example.JavaTaigaCode.models.TaskDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:config-test.properties")
public class BurndownChartServiceTest {
    @Test
    public void testCalculateTotalRunningSum_Successful() throws Exception {
        // Arrange
        int milestoneID = 123;
        String jsonResponse = "{\"id\":123,\"name\":\"Test Milestone\",\"total_points\":10.0,"
                + "\"estimated_start\":\"2024-01-01\",\"estimated_finish\":\"2024-01-10\","
                + "\"user_stories\":[{\"is_closed\":true,\"finish_date\":\"2024-01-03T12:00:00Z\",\"total_points\":2.0},"
                + "{\"is_closed\":true,\"finish_date\":\"2024-01-05T12:00:00Z\",\"total_points\":3.0},"
                + "{\"is_closed\":true,\"finish_date\":\"2024-01-08T12:00:00Z\",\"total_points\":1.0}]}";

        HttpClient httpClient = mock(HttpClient.class);
        HttpResponse httpResponse = mock(HttpResponse.class);
        HttpEntity httpEntity = new StringEntity(jsonResponse);
        when(httpResponse.getEntity()).thenReturn(httpEntity);
        when(httpResponse.getStatusLine())
                .thenReturn(new BasicStatusLine(HttpVersion.HTTP_1_1, HttpURLConnection.HTTP_OK, "OK"));
        when(httpClient.execute(any(HttpGet.class))).thenReturn(httpResponse);
        BurndownChart burndownChart = new BurndownChart();
        burndownChart.httpClient = httpClient;

        // Act
        MilestoneDTO milestone = burndownChart.calculateTotalRunningSum(milestoneID);

        // Assert
        assertNotNull(milestone);
        assertEquals(123, milestone.getMilestoneID());
        assertEquals("Test Milestone", milestone.getMilestoneName());
        assertEquals(10.0, milestone.getTotalPoints());
        assertEquals(LocalDate.parse("2024-01-01"), milestone.getStart_date());
        assertEquals(LocalDate.parse("2024-01-10"), milestone.getEnd_date());
        List<BurndownChartDTO> totalSumValue = milestone.getTotalSumValue();
        assertNotNull(totalSumValue);
        assertEquals(4, totalSumValue.size());
        Map<LocalDate, Double> expectedPointsMap = new TreeMap<>();
        expectedPointsMap.put(LocalDate.parse("2024-01-03"), 2.0);
        expectedPointsMap.put(LocalDate.parse("2024-01-05"), 3.0);
        expectedPointsMap.put(LocalDate.parse("2024-01-08"), 1.0);
    }

    @Test
    @DisplayName("Should return the business value for a user story successfully")
    public void testGetBusinessValueForUserStory_Successful() throws Exception {
        Integer userStoryId = 5468133;
        String jsonResponse = "{\"attributes_values\": {\"40211\": \"2\"}, \"version\": 2, \"user_story\": 5468133}";

        HttpClient httpClient = mock(HttpClient.class);
        HttpResponse httpResponse = mock(HttpResponse.class);
        HttpEntity httpEntity = new StringEntity(jsonResponse);

        when(httpResponse.getEntity()).thenReturn(httpEntity);
        when(httpResponse.getStatusLine())
                .thenReturn(new BasicStatusLine(HttpVersion.HTTP_1_1, HttpURLConnection.HTTP_OK, "OK"));
        when(httpClient.execute(any(HttpGet.class))).thenReturn(httpResponse);

        BurndownChart burndownChart = new BurndownChart();
        burndownChart.httpClient = httpClient;

        Integer businessValue = burndownChart.getBusinessValueForUserStory(userStoryId);

        assertNotNull(businessValue);
        assertEquals(2, businessValue);
    }

    @Test
    @DisplayName("Should the total running sum of business value for a milestone successfully")
    public void testGetTotalBusinessValue_Successful() throws Exception {
        Integer milestoneID = 376611;
        String jsonResponse = "{\"project\":1521717,\"project_extra_info\":{\"name\":\"SER516-Team-Denver\",\"slug\":\"ser516asu-ser516-team-denver\",\"logo_small_url\":null,\"id\":1521717},\"id\":376611,\"name\":\"Sprint1\",\"slug\":\"sprint-5-3543\",\"owner\":501364,\"estimated_start\":\"2024-01-29\",\"estimated_finish\":\"2024-02-18\",\"created_date\":\"2024-01-29T21:19:27.772Z\",\"modified_date\":\"2024-02-14T02:22:55.088Z\",\"closed\":false,\"disponibility\":0,\"order\":1,\"user_stories\":[{\"id\":5468109,\"finish_date\":\"2024-02-08T23:44:27.608Z\",\"is_closed\":true},{\"id\":5468111,\"finish_date\":\"2024-02-08T23:44:27.608Z\",\"is_closed\":true},{\"id\":5468122,\"finish_date\":\"2024-02-08T23:44:27.608Z\",\"is_closed\":true},{\"id\":5468133,\"finish_date\":\"2024-02-08T23:44:27.608Z\",\"is_closed\":true}],\"total_points\":72,\"closed_points\":35}";

        HttpClient httpClient = mock(HttpClient.class);
        HttpResponse httpResponse = mock(HttpResponse.class);
        HttpEntity httpEntity = new StringEntity(jsonResponse);
        BurndownChart burndownChartMock = mock(BurndownChart.class);
        when(burndownChartMock.getBusinessValueForUserStory(anyInt())).thenReturn(4);

        when(httpResponse.getEntity()).thenReturn(httpEntity);
        when(httpResponse.getStatusLine())
                .thenReturn(new BasicStatusLine(HttpVersion.HTTP_1_1, HttpURLConnection.HTTP_OK, "OK"));
        when(httpClient.execute(any(HttpGet.class))).thenReturn(httpResponse);

        BurndownChart burndownChart = new BurndownChart();
        burndownChart.httpClient = httpClient;

        MilestoneDTO milestone = burndownChart.getTotalBusinessValue(milestoneID);

        assertNotNull(milestone);
        List<BurndownChartDTO> totalSumValue = milestone.getTotalSumValue();
        assertNotNull(totalSumValue);
        assertEquals(4, totalSumValue.size());
        assertEquals("Sprint1", milestone.getMilestoneName());
        assertEquals(null, milestone.getPartialSumValue());
        assertEquals(LocalDate.parse("2024-01-29"), milestone.getStart_date());
        assertEquals(LocalDate.parse("2024-02-18"), milestone.getEnd_date());
        assertEquals(0, milestone.getTotalPoints());
    }

    @Test
    @DisplayName("Should get a list of tasks for a user story successfully")
    public void testGetUserStoryTasks_Successful() throws Exception {
        int userStoryID = 5468109;
        String jsonResponse = "[{\"id\":5330891,\"subject\":\"UnitTests(Backend)\",\"is_closed\":true,\"finished_date\":\"2024-02-08T04:50:48.864Z\"},{\"id\":5330892,\"subject\":\"Fetchprojectsfromback-endtodisplayalistandallowtheusertoselectaproject\",\"is_closed\":true,\"finished_date\":\"2024-02-06T21:54:32.325Z\"},{\"id\":5330894,\"subject\":\"Maintainalistofprojectids\",\"is_closed\":true,\"finished_date\":\"2024-02-04T00:43:59.007Z\"}]";

        HttpClient httpClient = mock(HttpClient.class);
        HttpResponse httpResponse = mock(HttpResponse.class);
        HttpEntity httpEntity = new StringEntity(jsonResponse);

        when(httpResponse.getEntity()).thenReturn(httpEntity);
        when(httpResponse.getStatusLine())
                .thenReturn(new BasicStatusLine(HttpVersion.HTTP_1_1, HttpURLConnection.HTTP_OK, "OK"));
        when(httpClient.execute(any(HttpGet.class))).thenReturn(httpResponse);
        BurndownChart burndownChart = new BurndownChart();
        burndownChart.httpClient = httpClient;

        List<TaskDTO> userStoryTasks = burndownChart.getUserStoryTasks(userStoryID);
        assertNotNull(userStoryTasks);
        assertEquals(3, userStoryTasks.size());
    }

    @Test
    @DisplayName("Should calculate the partial running sum of story points for a user story successfully")
    public void testCalculatePartialRunningSum_Successful() throws Exception {
        int milestoneID = 376611;
        String jsonResponse = "{\"project\":1521717,\"project_extra_info\":{\"name\":\"SER516-Team-Denver\",\"slug\":\"ser516asu-ser516-team-denver\",\"logo_small_url\":null,\"id\":1521717},\"id\":376611,\"name\":\"Sprint1\",\"slug\":\"sprint-5-3543\",\"owner\":501364,\"estimated_start\":\"2024-01-29\",\"estimated_finish\":\"2024-02-18\",\"created_date\":\"2024-01-29T21:19:27.772Z\",\"modified_date\":\"2024-02-14T02:22:55.088Z\",\"closed\":false,\"disponibility\":0,\"order\":1,\"user_stories\":[{\"id\":5468109,\"total_points\":3},{\"id\":5468111,\"total_points\":5}],\"total_points\":72,\"closed_points\":48}";

        HttpClient httpClient = mock(HttpClient.class);
        HttpResponse httpResponse = mock(HttpResponse.class);
        HttpEntity httpEntity = new StringEntity(jsonResponse);

        when(httpResponse.getEntity()).thenReturn(httpEntity);
        when(httpResponse.getStatusLine())
                .thenReturn(new BasicStatusLine(HttpVersion.HTTP_1_1, HttpURLConnection.HTTP_OK, "OK"));
        when(httpClient.execute(any(HttpGet.class))).thenReturn(httpResponse);
        BurndownChart burndownChart = new BurndownChart();
        BurndownChart burndownChartMock = mock(BurndownChart.class);

        List<TaskDTO> tasks = new ArrayList<>();
        tasks.add(new TaskDTO(5330891, "Task1", true, LocalDate.parse("2024-02-08")));
        tasks.add(new TaskDTO(5330892, "Task2", true, LocalDate.parse("2024-02-06")));
        tasks.add(new TaskDTO(5330894, "Task3", true, LocalDate.parse("2024-02-04")));
        when(burndownChartMock.getUserStoryTasks(anyInt())).thenReturn(tasks);

        burndownChart.httpClient = httpClient;

        MilestoneDTO milestone = burndownChart.calculatePartialRunningSum(milestoneID);
        assertNotNull(milestone);
        assertEquals(1, milestone.getPartialSumValue().size());
        assertEquals(72, milestone.getTotalPoints());
        assertEquals(72, milestone.getPartialSumValue().get(0).getValue());
        assertEquals("Sprint1", milestone.getMilestoneName());
    }
}
