package io.foodful.auth.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    public String redirect_uri;
    public String code;
    public String provider;

}
