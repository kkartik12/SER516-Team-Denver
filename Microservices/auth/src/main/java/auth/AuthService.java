package auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class AuthService {

    @Value("${taiga_api_endpoint}")
    private String TAIGA_API_ENDPOINT;

    public AuthModel authenticate(String username, String password) {
        // Endpoint to authenticate taiga's username and password
        String authEndpoint = TAIGA_API_ENDPOINT + "/auth";

        // Making an API call
        HttpPost request = new HttpPost(authEndpoint);
        String payload = "{\"type\":\"normal\",\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
        request.setEntity(new StringEntity(payload, ContentType.APPLICATION_JSON));

        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpResponse response = httpClient.execute(request);
            int httpStatus = response.getStatusLine().getStatusCode();
            if(httpStatus<200 || httpStatus>=300) {
                throw new RuntimeException(response.getStatusLine().toString());
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder result = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            return parseAuthToken(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    AuthModel parseAuthToken(String responseJson) throws Exception{
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseJson);
            Integer memberID = rootNode.path("id").asInt();
            String authToken = rootNode.path("auth_token").asText();
            return new AuthModel(memberID, authToken);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

}
