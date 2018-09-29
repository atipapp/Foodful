package io.foodful.authservice.service.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenValidationMessage {

    public String accessToken;

}
