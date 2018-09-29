package io.foodful.dinner.core.service.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DinnerResult {

    public String id;
    public String title;
    public String location;
    public String scheduledTime;
    public Map<String, String> guests;
    public String createdBy;

}
