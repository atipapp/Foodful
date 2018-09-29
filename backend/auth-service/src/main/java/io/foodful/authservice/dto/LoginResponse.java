package io.foodful.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    public String access_token;
    public String access_token_expires;
    public String refresh_token;
    public String refresh_token_expires;

}
