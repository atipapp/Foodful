package io.foodful.authservice.service.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResult {

    public String accessToken;
    public OffsetDateTime accessTokenExpires;
    public String refreshToken;
    public OffsetDateTime refreshTokenExpires;


}
