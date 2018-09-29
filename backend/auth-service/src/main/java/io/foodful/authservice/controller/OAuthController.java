package io.foodful.authservice.controller;

import io.foodful.authservice.dto.*;
import io.foodful.authservice.service.LoginService;
import io.foodful.authservice.service.TokenService;
import io.foodful.authservice.service.message.AccessTokenValidationResult;
import io.foodful.authservice.service.message.LoginResult;
import io.foodful.authservice.service.message.TokenResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.foodful.authservice.util.DTOMapper.*;

@RestController
@RequestMapping("/oauth")
public class OAuthController {

    private LoginService loginService;
    private TokenService tokenService;

    public OAuthController(LoginService loginService, TokenService tokenService) {
        this.loginService = loginService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login/social")
    public TokenResponse loginWithSocialProvider(@RequestBody LoginRequest request) {
        LoginResult result = loginService.login(loginRequestToMessage(request));
        return loginResultToResponse(result);
    }

    @PostMapping("/token")
    public TokenResponse renewAccessToken(@RequestBody AccessTokenRenewalRequest request) {
        TokenResult result = tokenService.renew(request.refreshToken);
        return tokenResultToResponse(result);
    }

    @PostMapping("/token/validate")
    public TokenValidationResponse validateAccessToken(@RequestBody TokenValidationRequest request) {
        AccessTokenValidationResult result = tokenService.validateAccessToken(request.accessToken);
        return tokenValidationResultToResponse(result);
    }

}
