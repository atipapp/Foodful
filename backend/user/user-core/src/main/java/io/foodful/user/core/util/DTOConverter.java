package io.foodful.user.core.util;

import io.foodful.user.api.dto.UserRequest;
import io.foodful.user.api.dto.UserResponse;
import io.foodful.user.core.service.message.UserMessage;
import io.foodful.user.core.service.message.UserResult;

public class DTOConverter {

    private DTOConverter() {
        throw new UnsupportedOperationException("Utility class");
    }


    public static UserMessage userRequestToMessage(UserRequest request) {
        return UserMessage.builder()
                .email(request.email)
                .firstName(request.firstName)
                .lastName(request.lastName)
                .externalId(request.externalId)
                .build();
    }

    public static UserResponse userResultToResponse(UserResult result) {
        return UserResponse.builder()
                .email(result.email)
                .firstName(result.firstName)
                .lastName(result.lastName)
                .userId(result.userId)
                .roles(result.roles)
                .build();
    }
}
