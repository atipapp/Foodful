package io.foodful.dinnerservice.endpoint;

import io.foodful.dinnerservice.DinnerServiceApplication;
import io.foodful.dinnerservice.dto.DinnerCreationRequest;
import io.foodful.dinnerservice.dto.DinnerResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DinnerServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class DinnerServiceIT {

    @LocalServerPort
    private int randomServerPort;

    protected WebTestClient client;

    @BeforeEach
    void setUpClient() {
        client = WebTestClient.bindToServer().baseUrl("http://localhost:" + randomServerPort).responseTimeout(Duration.ofMinutes(1)).build();
    }

    @Test
    public void createDinner() {
        DinnerCreationRequest request = DinnerCreationRequest.builder()
                .title("My first dinner")
                .location("D canteen")
                .scheduledTime(OffsetDateTime.now().plusDays(1).toString())
                .guests(Collections.singletonList("userIdGoesHere"))
                .build();


        DinnerResponse response = client.post().uri("/dinner").syncBody(request)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(DinnerResponse.class)
                .returnResult().getResponseBody();

        assertNotNull(response.id);
        assertEquals(request.title, response.title);
        assertEquals(request.location, response.location);
        assertEquals(request.scheduledTime, response.scheduledTime);
        assertTrue(request.guests.stream().anyMatch(guest -> response.guests.containsKey(guest)));
    }

}
