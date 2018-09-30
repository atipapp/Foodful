package io.foodful.user.core.endpoint;

import io.foodful.user.api.UserRequest;
import io.foodful.user.api.UserResponse;
import io.foodful.user.core.UserServiceApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = UserServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class UserServiceIT {

    @LocalServerPort
    private int randomServerPort;

    protected WebTestClient client;

    @BeforeEach
    void setUpClient() {
        client = WebTestClient.bindToServer().baseUrl("http://localhost:" + randomServerPort).responseTimeout(Duration.ofMinutes(1)).build();
    }

    @Test
    void createUser() {
        UserRequest request = UserRequest.builder()
                .firstName("David")
                .lastName("Hasselhoff")
                .email("david@hasselhoff.com")
                .build();

        UserResponse response = client.post().uri("/user").syncBody(request)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(UserResponse.class)
                .returnResult().getResponseBody();

        assertEquals(request.email, response.email);
        assertEquals(request.firstName, response.firstName);
        assertEquals(request.lastName, response.lastName);
    }

}
