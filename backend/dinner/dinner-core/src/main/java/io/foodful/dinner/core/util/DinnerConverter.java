package io.foodful.dinner.core.util;

import io.foodful.dinner.api.DinnerCreateRequest;
import io.foodful.dinner.api.DinnerInviteRequest;
import io.foodful.dinner.api.DinnerResponse;
import io.foodful.dinner.api.DinnerUpdateRequest;
import io.foodful.dinner.core.service.message.DinnerCreationMessage;
import io.foodful.dinner.core.service.message.DinnerInviteMessage;
import io.foodful.dinner.core.service.message.DinnerResult;
import io.foodful.dinner.core.service.message.DinnerUpdateMessage;

import java.time.OffsetDateTime;

public class DinnerConverter {

    private DinnerConverter() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static DinnerCreationMessage dinnerCreateRequestToMessage(DinnerCreateRequest request, String userId) {
        return DinnerCreationMessage.builder()
                .title(request.title)
                .location(request.location)
                .scheduledTime(OffsetDateTime.parse(request.scheduledTime))
                .guests(request.guests)
                .userId(userId)
                .build();
    }

    public static DinnerUpdateMessage dinnerUpdateRequestToMessage(String dinnerId, DinnerUpdateRequest request, String principalUserId) {
        return DinnerUpdateMessage.builder()
                .dinnerId(dinnerId)
                .title(request.title)
                .location(request.location)
                .scheduledTime(request.scheduledTime.map(OffsetDateTime::parse))
                .principalUserId(principalUserId)
                .build();
    }

    public static DinnerInviteMessage dinnerInviteRequestToMessage(String dinnerId, DinnerInviteRequest request, String principalUserId) {
        return DinnerInviteMessage.builder()
                .dinnerId(dinnerId)
                .invitedUserId(request.userId)
                .principalUserId(principalUserId)
                .build();
    }

    public static DinnerResponse dinnerResultToResponse(DinnerResult result) {
        return DinnerResponse.builder()
                .id(result.id)
                .location(result.location)
                .scheduledTime(result.scheduledTime)
                .title(result.title)
                .guests(result.guests)
                .createdBy(result.createdBy)
                .build();
    }

}
