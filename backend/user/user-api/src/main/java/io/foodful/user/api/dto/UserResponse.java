package io.foodful.user.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    public String firstName;
    public String lastName;
    public String email;
    public String userId;
    public List<String> roles;

}
