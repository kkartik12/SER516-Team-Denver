package org.example.JavaTaigaCode.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicStatusLine;
import org.example.JavaTaigaCode.models.AdoptedWorkDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AdoptedWorkServiceTest {
    @Mock
    private HttpClient httpClient;

    @InjectMocks
    private AdoptedWorkService adoptedWorkService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetUSAddedAfterSprintPlanning_ErrorResponse() throws IOException {
        // Mock HTTP response with error
        String errorMessage = "Internal Server Error";
        HttpResponse httpResponse = TestUtils.createMockHttpResponse(errorMessage, 500);
        when(httpClient.execute(ArgumentMatchers.any(HttpGet.class))).thenReturn(httpResponse);

        // Test the method
        AdoptedWorkDTO result = adoptedWorkService.getUSAddedAfterSprintPlanning(123);

        // Verify the result is null
        assertEquals(null, result);
    }
}

class TestUtils {
    static HttpResponse createMockHttpResponse(String responseBody, int statusCode) throws IOException {
        HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
        HttpEntity httpEntity = new StringEntity(responseBody);
        when(httpResponse.getEntity()).thenReturn(httpEntity);
        when(httpResponse.getStatusLine()).thenReturn(new BasicStatusLine(HttpVersion.HTTP_1_1, statusCode, ""));
        return httpResponse;
    }
}