package org.example.JavaTaigaCode.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.ByteArrayInputStream;
import java.io.IOException;

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

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse httpResponse;

    @Mock
    private HttpEntity httpEntity;

    @Test
    public void testAuthenticate_Successful() throws Exception {
        // Arrange
        String username = "testUser";
        String password = "testPassword";
        String jsonResponse = "{\"id\":123,\"auth_token\":\"testAuthToken\"}";

        when(httpResponse.getEntity()).thenReturn(httpEntity);
        when(httpEntity.getContent()).thenReturn(new ByteArrayInputStream(jsonResponse.getBytes()));

        when(httpClient.execute(any(HttpPost.class))).thenReturn(httpResponse);

        // Act
        Integer memberId = authentication.authenticate(username, password);

        // Assert
        assertEquals(123, memberId);
    }

    @Test
    public void testAuthenticate_Exception() throws Exception {
        // Arrange
        String username = "testUser";
        String password = "testPassword";

        when(httpClient.execute(any(HttpPost.class))).thenThrow(IOException.class);

        // Act
        Integer memberId = authentication.authenticate(username, password);

        // Assert
        assertEquals(null, memberId);
    }

    @Test
    public void testParseAuthToken_Successful() throws Exception {
        // Arrange
        String jsonResponse = "{\"id\":123,\"auth_token\":\"testAuthToken\"}";

        // Act
        Integer memberId = authentication.parseAuthToken(jsonResponse);

        // Assert
        assertEquals(123, Authentication.memberID);
        assertEquals("testAuthToken", Authentication.authToken);
        assertEquals(123, memberId);
    }

    @Test
    public void testParseAuthToken_Exception() throws Exception {
        // Arrange
        String jsonResponse = "{\"invalid_json\":}";

        // Act
        Integer memberId = authentication.parseAuthToken(jsonResponse);

        // Assert
        assertEquals(null, Authentication.memberID);
        assertEquals(null, Authentication.authToken);
        assertEquals(null, memberId);
    }
}
