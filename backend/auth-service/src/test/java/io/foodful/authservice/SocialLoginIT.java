package io.foodful.authservice;

import io.foodful.authservice.dto.LoginRequest;
import io.foodful.authservice.dto.LoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = AuthServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class SocialLoginIT {

    @LocalServerPort
    private int randomServerPort;

    protected WebTestClient client;

    @BeforeEach
    void setUpClient() {
        client = WebTestClient.bindToServer().baseUrl("http://localhost:" + randomServerPort).responseTimeout(Duration.ofMinutes(1)).build();
    }

    @Test
    void exchangeAuthCodeForAccessAndRefreshTokens() {

        LoginRequest request = LoginRequest.builder()
                .code("MockCode")
                .redirect_uri("localhost")
                .provider("FACEBOOK")
                .build();

        LoginResponse response = client.post().uri("/oauth/login/social").syncBody(request)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(LoginResponse.class)
                .returnResult().getResponseBody();

        assertNotNull(response.access_token);
        assertNotNull(response.refresh_token);
        assertNotNull(response.access_token_expires);
        assertNotNull(response.refresh_token_expires);
    }

}
