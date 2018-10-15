package io.foodful.auth.core;

import io.foodful.auth.api.dto.InternalJwtCreationRequest;
import io.foodful.auth.api.dto.InternalJwtCreationResponse;
import io.foodful.user.api.UserClient;
import io.foodful.user.api.dto.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = AuthServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class InternalJwtIt extends EndpointTestBase {

    @MockBean
    UserClient userClient;

    @BeforeEach
    void setUpMocks() {
        setUpUserClientMock();
    }

    @Test
    void createInternalJwtForUser() {
        InternalJwtCreationRequest request = InternalJwtCreationRequest.builder()
                .userId(UUID.randomUUID().toString())
                .build();

        InternalJwtCreationResponse response = client.post().uri("/jwt").syncBody(request)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(InternalJwtCreationResponse.class)
                .returnResult().getResponseBody();

        assertNotNull(response.jwt);
    }

    private void setUpUserClientMock() {
        when(userClient.getUser(any())).thenAnswer((Answer<UserResponse>) invocationOnMock -> {
            String argument = invocationOnMock.getArgument(0);
            return UserResponse.builder()
                    .email("david@hasselhoff.com")
                    .firstName("David")
                    .lastName("Hasselhoff")
                    .userId(argument)
                    .roles(Collections.singletonList("USER"))
                    .build();
        });
    }

}
