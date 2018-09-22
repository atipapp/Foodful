package io.foodful.dinnerservice.util;

import io.foodful.dinnerservice.dto.DinnerCreationRequest;
import io.foodful.dinnerservice.dto.DinnerResponse;
import io.foodful.dinnerservice.service.message.DinnerCreationMessage;
import io.foodful.dinnerservice.service.message.DinnerResult;

import java.time.OffsetDateTime;

public class DinnerConverter {

    private DinnerConverter() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static DinnerCreationMessage dinnerCreationRequestToMessage(DinnerCreationRequest request) {
        return DinnerCreationMessage.builder()
                .title(request.title)
                .location(request.location)
                .scheduledTime(OffsetDateTime.parse(request.scheduledTime))
                .guests(request.guests)
                .build();
    }

    public static DinnerResponse dinnerResultToResponse(DinnerResult result) {
        return DinnerResponse.builder()
                .id(result.id)
                .location(result.location)
                .scheduledTime(result.scheduledTime)
                .title(result.title)
                .guests(result.guests)
                .build();
    }

}
