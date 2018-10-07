package io.foodful.auth.core.controller;

import io.foodful.auth.api.dto.*;
import io.foodful.auth.core.service.LoginService;
import io.foodful.auth.core.service.TokenService;
import io.foodful.auth.core.service.message.AccessTokenValidationResult;
import io.foodful.auth.core.service.message.LoginResult;
import io.foodful.auth.core.service.message.TokenResult;
import io.foodful.auth.core.util.DTOMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        LoginResult result = loginService.login(DTOMapper.loginRequestToMessage(request));
        return DTOMapper.loginResultToResponse(result);
    }

    @PostMapping("/token")
    public TokenResponse renewAccessToken(@RequestBody AccessTokenRenewalRequest request) {
        TokenResult result = tokenService.renew(request.refresh_token);
        return DTOMapper.tokenResultToResponse(result);
    }

    @PostMapping("/token/validate")
    public TokenValidationResponse validateAccessToken(@RequestBody TokenValidationRequest request) {
        AccessTokenValidationResult result = tokenService.validateAccessToken(request.access_token);
        return DTOMapper.tokenValidationResultToResponse(result);
    }

}
