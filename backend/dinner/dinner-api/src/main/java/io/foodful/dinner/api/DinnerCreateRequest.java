package io.foodful.dinner.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DinnerCreateRequest {

    public String title;
    public String location;
    public String scheduledTime;
    public List<String> guests;

}
