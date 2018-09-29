package io.foodful.authservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenRenewalRequest {

    @JsonProperty("refresh_token")
    public String refreshToken;

}
