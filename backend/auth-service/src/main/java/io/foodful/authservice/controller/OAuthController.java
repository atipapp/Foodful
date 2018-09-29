package io.foodful.authservice.controller;

import io.foodful.authservice.dto.LoginRequest;
import io.foodful.authservice.dto.LoginResponse;
import io.foodful.authservice.service.LoginService;
import io.foodful.authservice.service.message.LoginResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.foodful.authservice.util.DTOMapper.loginRequestToMessage;
import static io.foodful.authservice.util.DTOMapper.loginResultToResponse;

@RestController
@RequestMapping("/oauth")
public class OAuthController {

    private LoginService service;

    public OAuthController(LoginService service) {
        this.service = service;
    }

    @PostMapping("/login/social")
    public LoginResponse loginWithSocialProvider(@RequestBody LoginRequest request) {
        LoginResult result = service.login(loginRequestToMessage(request));
        return loginResultToResponse(result);
    }

}
