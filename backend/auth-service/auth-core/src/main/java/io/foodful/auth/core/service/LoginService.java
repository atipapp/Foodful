package io.foodful.auth.core.service;

import feign.FeignException;
import io.foodful.auth.core.error.InvalidTokenException;
import io.foodful.auth.core.service.facebook.FacebookAccessTokenResponse;
import io.foodful.auth.core.service.facebook.FacebookClient;
import io.foodful.auth.core.service.message.TokenResult;
import io.foodful.auth.core.service.message.LoginMessage;
import io.foodful.auth.core.service.message.LoginResult;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.UUID;

@Service
@Slf4j
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
        getUserInfoFromFacebook(facebookAccessToken);

        return tokenResultToLoginResult(tokenService.create("MockUserId" + UUID.randomUUID().toString()));
    }

    private void getUserInfoFromFacebook(FacebookAccessTokenResponse token) {
        String appSecretProof = sha256(token.accessToken, facebookClientSecret);

        try {
            facebookClient.getUserData(getAuthorizationHeaderValue(token.accessToken), appSecretProof);
        } catch (FeignException exception) {
            if (exception.status() == 401){
                throw new InvalidTokenException();
            }
        }
    }

    private LoginResult tokenResultToLoginResult(TokenResult result) {
        return LoginResult.builder()
                .accessToken(result.accessToken)
                .accessTokenExpires(result.accessTokenExpires)
                .refreshToken(result.refreshToken)
                .refreshTokenExpires(result.refreshTokenExpires)
                .build();
    }

    private String getAuthorizationHeaderValue(String token) {
        return "Bearer " + token;
    }

    private String sha256(String accessToken, String appSecret) {
        String result = "";

        try {
            String algorithm = "HmacSHA256";
            SecretKeySpec key = new SecretKeySpec(appSecret.getBytes(), algorithm);
            Mac mac = Mac.getInstance(algorithm);
            mac.init(key);
            byte[] bytes = mac.doFinal(accessToken.getBytes("UTF-8"));
            result = new String(Hex.encode(bytes));
        } catch (Exception error) {
            log.error("Encoding app secret proof failed.");
        }

        return result;
    }

}
