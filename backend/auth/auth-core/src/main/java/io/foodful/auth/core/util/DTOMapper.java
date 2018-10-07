package io.foodful.auth.core.util;

import io.foodful.auth.api.dto.LoginRequest;
import io.foodful.auth.api.dto.TokenResponse;
import io.foodful.auth.api.dto.TokenValidationResponse;
import io.foodful.auth.core.service.LoginService;
import io.foodful.auth.core.service.message.AccessTokenValidationResult;
import io.foodful.auth.core.service.message.LoginMessage;
import io.foodful.auth.core.service.message.LoginResult;
import io.foodful.auth.core.service.message.TokenResult;

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

    public static TokenValidationResponse tokenValidationResultToResponse(AccessTokenValidationResult result) {
        return TokenValidationResponse.builder()
                .isValid(result.isValid)
                .userId(result.userId)
                .build();
    }

}
