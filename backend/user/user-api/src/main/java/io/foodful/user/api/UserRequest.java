package io.foodful.user.api;

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

}