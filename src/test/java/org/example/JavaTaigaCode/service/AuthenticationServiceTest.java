package org.example.JavaTaigaCode.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:config-test.properties")
public class AuthenticationServiceTest {
    @InjectMocks
    private Authentication authentication;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    private static HttpClient httpClient;

    private static HttpResponse httpResponse;

    private static StatusLine statusLine;

    private static HttpEntity httpEntity;

    @BeforeAll
    public static void init() {
        httpClient = Mockito.mock(HttpClient.class);
        httpResponse = Mockito.mock(HttpResponse.class);
        statusLine = Mockito.mock(StatusLine.class);
        httpEntity = Mockito.mock(HttpEntity.class);
    }

    @Test
    public void testAuthenticate_Exception() throws Exception {
        String username = "testUser";
        String password = "testPassword";
        String responseJson = "{\"code\":\"Unauthorized\"}";

        when(httpEntity.getContent()).thenReturn(new ByteArrayInputStream(responseJson.getBytes()));
        when(statusLine.getStatusCode()).thenReturn(401);
        when(httpResponse.getStatusLine()).thenReturn(statusLine);
        when(httpResponse.getEntity()).thenReturn(httpEntity);
        when(httpClient.execute(Mockito.any(HttpPost.class))).thenReturn(httpResponse);

        Integer memberId = authentication.authenticate(username, password);

        assertEquals(null, memberId);
    }

    @Test
    public void testParseAuthToken_Successful() throws Exception {
        String jsonResponse = "{\"id\":123,\"auth_token\":\"testAuthToken\"}";

        Integer memberId = authentication.parseAuthToken(jsonResponse);

        assertEquals(123, Authentication.memberID);
        assertEquals("testAuthToken", Authentication.authToken);
        assertEquals(123, memberId);
    }
}
