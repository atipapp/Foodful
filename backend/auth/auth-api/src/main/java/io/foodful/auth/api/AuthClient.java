package io.foodful.auth.api;

import io.foodful.auth.api.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth-service-v1", url = "${foodful.clients.prefix}auth-service-v1${foodful.clients.postfix}")
public interface AuthClient {

    @PostMapping("/oauth/login/social")
    TokenResponse loginWithSocialProvider(@RequestBody LoginRequest request);

    @PostMapping("/oauth/token")
    TokenResponse renewAccessToken(@RequestBody AccessTokenRenewalRequest request);

    @PostMapping("/oauth/token/validate")
    TokenValidationResponse validateAccessToken(@RequestBody TokenValidationRequest request);

}