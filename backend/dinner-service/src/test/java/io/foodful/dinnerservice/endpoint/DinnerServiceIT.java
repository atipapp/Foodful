package io.foodful.dinnerservice.endpoint;

import io.foodful.dinnerservice.DinnerServiceApplication;
import io.foodful.dinnerservice.dto.DinnerCreateRequest;
import io.foodful.dinnerservice.dto.DinnerResponse;
import io.foodful.dinnerservice.dto.DinnerUpdateRequest;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

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
    void createDinnerTest() {
        DinnerCreateRequest request = DinnerCreateRequest.builder()
                .title("My first dinner")
                .location("D canteen")
                .scheduledTime(OffsetDateTime.now().plusDays(1).toString())
                .guests(Collections.singletonList("userIdGoesHere"))
                .build();

        DinnerResponse response = createDinner(request);

        assertNotNull(response.id);
        assertEquals(request.title, response.title);
        assertEquals(request.location, response.location);
        assertEquals(request.scheduledTime, response.scheduledTime);
        assertTrue(request.guests.stream().anyMatch(guest -> response.guests.containsKey(guest)));
    }

    @Test
    void getDinnerTest() {
        DinnerResponse expected = withOneDinner();

        DinnerResponse response = getDinner(expected.id);

        assertEquals(expected.id, response.id);
        assertEquals(expected.title, response.title);
        assertEquals(expected.location, response.location);
        assertTrue(Duration.between(OffsetDateTime.parse(expected.scheduledTime), OffsetDateTime.parse(response.scheduledTime)).getSeconds() <= 1);
        assertTrue(expected.guests.keySet().containsAll(response.guests.keySet()));
    }

    @Test
    void updateDinnerTest() {
        DinnerResponse beforeUpdate = withOneDinner();

        DinnerUpdateRequest request = DinnerUpdateRequest.builder()
                .title(Optional.of("Updated title"))
                .location(Optional.of("Updated Location"))
                .scheduledTime(Optional.of(OffsetDateTime.now().plusDays(1).toString()))
                .build();

        DinnerResponse afterUpdate = updateDinner(beforeUpdate.id, request);

        assertNotNull(afterUpdate.id);
        assertEquals(request.title.get(), afterUpdate.title);
        assertEquals(request.location.get(), afterUpdate.location);
        assertEquals(request.scheduledTime.get(), afterUpdate.scheduledTime);
        assertTrue(beforeUpdate.guests.keySet().containsAll(afterUpdate.guests.keySet()));
    }

    @Test
    void deleteDinnerTest() {
        DinnerResponse dinner = withOneDinner();

        deleteDinner(dinner.id);

        client.get().uri("/dinner/" + dinner.id)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isNotFound();
    }


    @Test
    void getDinnerNotFound() {
        client.get().uri("/dinner/thisShouldNotBeFound")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void updateDinnerNotFound() {
        DinnerUpdateRequest request = DinnerUpdateRequest.builder()
                .title(Optional.of("Not found updated title"))
                .location(Optional.of("Not found updated Location"))
                .scheduledTime(Optional.of(OffsetDateTime.now().plusDays(1).toString()))
                .build();

        client.put().uri("/dinner/thisShouldNotBeFound").syncBody(request)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void deleteDinnerNotFound() {
        client.delete().uri("/dinner/thisShouldNotBeFound")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isNotFound();
    }

    private DinnerResponse withOneDinner() {
        DinnerCreateRequest request = DinnerCreateRequest.builder()
                .title(UUID.randomUUID().toString())
                .location("Mock Location")
                .scheduledTime(OffsetDateTime.now().plusDays(1).toString())
                .guests(Arrays.asList(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString()))
                .build();

        return createDinner(request);
    }

    private DinnerResponse createDinner(DinnerCreateRequest request) {
        return client.post().uri("/dinner").syncBody(request)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(DinnerResponse.class)
                .returnResult().getResponseBody();
    }

    private DinnerResponse getDinner(String dinnerId) {
        return client.get().uri("/dinner/" + dinnerId)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(DinnerResponse.class)
                .returnResult().getResponseBody();
    }

    private DinnerResponse updateDinner(String dinnerId, DinnerUpdateRequest request) {
        return client.put().uri("/dinner/" + dinnerId).syncBody(request)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(DinnerResponse.class)
                .returnResult().getResponseBody();
    }

    private void deleteDinner(String dinnerId) {
        client.delete().uri("/dinner/" + dinnerId)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk();
    }

}