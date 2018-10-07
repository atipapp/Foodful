package io.foodful.user.core.endpoint;

import io.foodful.user.api.dto.UserRequest;
import io.foodful.user.api.dto.UserResponse;
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
import java.util.UUID;

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

        UserResponse response = createUser(request);

        assertEquals(request.email, response.email);
        assertEquals(request.firstName, response.firstName);
        assertEquals(request.lastName, response.lastName);
    }


    @Test
    void getUser() {
        UserResponse user = withOneUser();
        UserResponse response = getUserById(user.userId);

        assertEquals(user.userId, response.userId);
        assertEquals(user.email, response.email);
        assertEquals(user.firstName, response.firstName);
        assertEquals(user.lastName, response.lastName);
    }

    private UserResponse getUserById(String userId) {
        return client.get().uri("/user/" + userId)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(UserResponse.class)
                .returnResult().getResponseBody();
    }

    private UserResponse withOneUser() {
        return createUser(UserRequest.builder()
                .firstName("Mock")
                .lastName("Hasselhoff")
                .email(UUID.randomUUID().toString() + "@hasselhoff.com")
                .build());
    }

    private UserResponse createUser(UserRequest request) {
        return client.post().uri("/user").syncBody(request)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(UserResponse.class)
                .returnResult().getResponseBody();
    }

}
