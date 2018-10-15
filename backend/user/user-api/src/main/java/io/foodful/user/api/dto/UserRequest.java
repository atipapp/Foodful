package io.foodful.user.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    public String firstName;
    public String lastName;
    public String email;
    public String externalId;

}