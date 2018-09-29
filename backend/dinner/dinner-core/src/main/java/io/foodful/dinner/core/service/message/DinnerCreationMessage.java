package io.foodful.dinner.core.service.message;

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
