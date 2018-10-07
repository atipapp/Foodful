package io.foodful.auth.core;

import io.foodful.auth.api.dto.*;
import io.foodful.auth.core.service.facebook.FacebookAccessTokenResponse;
import io.foodful.auth.core.service.facebook.FacebookClient;
import io.foodful.auth.core.service.facebook.FacebookUserResponse;
import io.foodful.user.api.UserClient;
import io.foodful.user.api.dto.UserRequest;
import io.foodful.user.api.dto.UserResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = AuthServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class SocialLoginIT {

    @LocalServerPort
    private int randomServerPort;

    protected WebTestClient client;

    @MockBean
    private FacebookClient facebookClient;

    @MockBean
    private UserClient userClient;

    @BeforeEach
    void setUpClient() {
        client = WebTestClient.bindToServer().baseUrl("http://localhost:" + randomServerPort).responseTimeout(Duration.ofMinutes(1)).build();

        setUpFacebookClientMock();
        setUpUserClientMock();
    }

    @Test
    void exchangeAuthCodeForAccessAndRefreshTokens() {
        LoginRequest request = LoginRequest.builder()
                .code("MockCode")
                .redirect_uri("localhost")
                .provider("FACEBOOK")
                .build();

        TokenResponse response = socialLogin(request);

        assertNotNull(response.access_token);
        assertNotNull(response.refresh_token);
        assertNotNull(response.access_token_expires);
        assertNotNull(response.refresh_token_expires);
    }

    @Test
    void renewAccessTokenWithRefreshToken() {
        TokenResponse login = withOneLogin();

        AccessTokenRenewalRequest request = AccessTokenRenewalRequest.builder()
                .refresh_token(login.refresh_token)
                .build();

        TokenResponse response = client.post().uri("/oauth/token").syncBody(request)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(TokenResponse.class)
                .returnResult().getResponseBody();

        assertNotNull(response.access_token);
        assertNotEquals(login.access_token, response.access_token);
        assertNotEquals(login.access_token_expires, response.access_token_expires);
        assertNotEquals(login.refresh_token, response.refresh_token);
    }

    @Test
    void validateAccessToken() {
        TokenResponse login = withOneLogin();

        TokenValidationRequest request = TokenValidationRequest.builder()
                .access_token(login.access_token)
                .build();

        TokenValidationResponse response = client.post().uri("/oauth/token/validate").syncBody(request)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(TokenValidationResponse.class)
                .returnResult().getResponseBody();

        assertNotNull(response.userId);
        assertTrue(response.isValid);
    }

    private TokenResponse withOneLogin() {
        LoginRequest request = LoginRequest.builder()
                .code(generateRandomToken(50))
                .redirect_uri("localhost")
                .provider("FACEBOOK")
                .build();

        return socialLogin(request);
    }

    private TokenResponse socialLogin(LoginRequest request) {
        return client.post().uri("/oauth/login/social").syncBody(request)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(TokenResponse.class)
                .returnResult().getResponseBody();
    }

    private void setUpFacebookClientMock() {
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

    private void setUpUserClientMock() {
        when(userClient.createOrUpdateUser(any())).thenAnswer((Answer<UserResponse>) invocationOnMock -> {
            UserRequest argument = invocationOnMock.getArgument(0);
            return UserResponse.builder()
                    .email(argument.email)
                    .firstName(argument.firstName)
                    .lastName(argument.lastName)
                    .email(argument.email)
                    .userId(UUID.randomUUID().toString())
                    .build();
        });
    }

    private String generateRandomToken(int length) {
        return RandomStringUtils.random(length, 0, 0,
                true, true, null, new SecureRandom());
    }

}
