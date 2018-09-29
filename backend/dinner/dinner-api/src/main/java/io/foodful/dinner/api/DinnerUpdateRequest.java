package io.foodful.dinner.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DinnerUpdateRequest {

    public Optional<String> title;
    public Optional<String> location;
    public Optional<String> scheduledTime;
    public Optional<List<String>> guests;

}
