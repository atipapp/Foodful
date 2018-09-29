package io.foodful.authservice.util;

import io.foodful.authservice.dto.LoginRequest;
import io.foodful.authservice.dto.TokenResponse;
import io.foodful.authservice.service.LoginService;
import io.foodful.authservice.service.message.LoginMessage;
import io.foodful.authservice.service.message.LoginResult;
import io.foodful.authservice.service.message.TokenResult;

public class DTOMapper {

    private DTOMapper() {
        throw new UnsupportedOperationException("Utility class");
    }


    public static LoginMessage loginRequestToMessage(LoginRequest request) {
        return LoginMessage.builder()
                .redirect_uri(request.redirect_uri)
                .code(request.code)
                .provider(LoginService.LoginProvider.valueOf(request.provider))
                .build();
    }

    public static TokenResponse loginResultToResponse(LoginResult result) {
        return TokenResponse.builder()
                .access_token(result.accessToken)
                .access_token_expires(result.accessTokenExpires.toString())
                .refresh_token(result.refreshToken)
                .refresh_token_expires(result.refreshTokenExpires.toString())
                .build();
    }

    public static TokenResponse tokenResultToResponse(TokenResult result) {
        return TokenResponse.builder()
                .access_token(result.accessToken)
                .access_token_expires(result.accessTokenExpires.toString())
                .refresh_token(result.refreshToken)
                .refresh_token_expires(result.refreshTokenExpires.toString())
                .build();
    }
}
