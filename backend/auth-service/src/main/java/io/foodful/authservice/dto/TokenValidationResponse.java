package io.foodful.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenValidationResponse {

    public String userId;
    public boolean isValid;

}
