package io.foodful.dinnerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DinnerResponse {

    public String id;
    public String title;
    public String location;
    public String scheduledTime;
    public Map<String, String> guests;
    public String createdBy;

}
