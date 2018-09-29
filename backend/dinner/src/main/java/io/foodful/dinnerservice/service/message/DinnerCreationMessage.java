package io.foodful.dinnerservice.service.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DinnerCreationMessage {

    public String title;
    public String location;
    public OffsetDateTime scheduledTime;
    public List<String> guests;
    public String userId;

}
