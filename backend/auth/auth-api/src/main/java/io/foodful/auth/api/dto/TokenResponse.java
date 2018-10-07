package io.foodful.auth.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {

    public String access_token;
    public String access_token_expires;
    public String refresh_token;
    public String refresh_token_expires;

}
