package io.foodful.authservice.service;

import io.foodful.authservice.service.facebook.FacebookAccessTokenResponse;
import io.foodful.authservice.service.facebook.FacebookClient;
import io.foodful.authservice.service.message.TokenResult;
import io.foodful.authservice.service.message.LoginMessage;
import io.foodful.authservice.service.message.LoginResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoginService {

    public enum LoginProvider {
        FACEBOOK
    }

    private TokenService tokenService;
    private FacebookClient facebookClient;
    private String facebookClientId;
    private String facebookClientSecret;


    public LoginService(TokenService tokenService,
                        FacebookClient facebookClient,
                        @Value("${foodful.auth.social.facebook.client-secret}") String facebookClientSecret,
                        @Value("${foodful.auth.social.facebook.client-id}") String facebookClientId) {
        this.tokenService = tokenService;
        this.facebookClient = facebookClient;
        this.facebookClientId = facebookClientId;
        this.facebookClientSecret = facebookClientSecret;
    }

    public LoginResult login(LoginMessage message) {

        FacebookAccessTokenResponse facebookAccessToken = facebookClient.getAccessToken(facebookClientId, message.redirect_uri, facebookClientSecret, message.code);
        facebookClient.getUserData(facebookAccessToken.accessToken, facebookClientSecret);
        // TODO: call to user-service, which returns a userId

        return tokenResultToLoginResult(tokenService.createTokensForUser("MockUserId" + UUID.randomUUID().toString()));
    }

    private LoginResult tokenResultToLoginResult(TokenResult result) {
        return LoginResult.builder()
                .accessToken(result.accessToken)
                .accessTokenExpires(result.accessTokenExpires)
                .refreshToken(result.refreshToken)
                .refreshTokenExpires(result.refreshTokenExpires)
                .build();
    }


}
