package io.foodful.dinnerservice.util;

import io.foodful.dinnerservice.dto.DinnerCreateRequest;
import io.foodful.dinnerservice.dto.DinnerInviteRequest;
import io.foodful.dinnerservice.dto.DinnerResponse;
import io.foodful.dinnerservice.dto.DinnerUpdateRequest;
import io.foodful.dinnerservice.service.message.DinnerCreationMessage;
import io.foodful.dinnerservice.service.message.DinnerInviteMessage;
import io.foodful.dinnerservice.service.message.DinnerResult;
import io.foodful.dinnerservice.service.message.DinnerUpdateMessage;

import java.time.OffsetDateTime;

public class DinnerConverter {

    private DinnerConverter() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static DinnerCreationMessage dinnerCreateRequestToMessage(DinnerCreateRequest request) {
        return DinnerCreationMessage.builder()
                .title(request.title)
                .location(request.location)
                .scheduledTime(OffsetDateTime.parse(request.scheduledTime))
                .guests(request.guests)
                .build();
    }

    public static DinnerUpdateMessage dinnerUpdateRequestToMessage(String dinnerId, DinnerUpdateRequest request) {
        return DinnerUpdateMessage.builder()
                .dinnerId(dinnerId)
                .title(request.title)
                .location(request.location)
                .scheduledTime(request.scheduledTime.map(OffsetDateTime::parse))
                .build();
    }

    public static DinnerInviteMessage dinnerInviteRequestToMessage(String dinnerId, DinnerInviteRequest request) {
        return DinnerInviteMessage.builder()
                .dinnerId(dinnerId)
                .userId(request.userId)
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
