package io.foodful.dinnerservice.service.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Optional;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DinnerUpdateMessage {

    public String dinnerId;
    public Optional<String> title;
    public Optional<String> location;
    public Optional<OffsetDateTime> scheduledTime;

}
