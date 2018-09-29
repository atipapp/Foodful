package io.foodful.auth.api;

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
