package io.foodful.authservice;

import io.foodful.authservice.dto.LoginRequest;
import io.foodful.authservice.dto.LoginResponse;
import io.foodful.authservice.service.facebook.FacebookAccessTokenResponse;
import io.foodful.authservice.service.facebook.FacebookClient;
import io.foodful.authservice.service.facebook.FacebookUserResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@SpringBootTest(classes = AuthServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class SocialLoginIT {

    @LocalServerPort
    private int randomServerPort;

    protected WebTestClient client;

    @MockBean
    private FacebookClient facebookClient;

    @BeforeEach
    void setUpClient() {
        client = WebTestClient.bindToServer().baseUrl("http://localhost:" + randomServerPort).responseTimeout(Duration.ofMinutes(1)).build();

        doReturn(FacebookAccessTokenResponse.builder()
                .accessToken(generateRandomToken(50))
                .expiresInSeconds(720)
                .tokenType("access")
                .build())
                .when(facebookClient).getAccessToken(any(), any(), any(), any());

        doReturn(FacebookUserResponse.builder()
                .email("david@hasselhoff.com")
                .firstName("David")
                .lastName("Hasselhoff")
                .id(UUID.randomUUID().toString())
                .build())
                .when(facebookClient).getUserData(any(), any());
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

    private String generateRandomToken(int length) {
        return RandomStringUtils.random(length, 0, 0,
                true, true, null, new SecureRandom());
    }

}
