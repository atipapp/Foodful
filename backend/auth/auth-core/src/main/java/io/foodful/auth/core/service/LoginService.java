package io.foodful.auth.core.service;

import feign.FeignException;
import io.foodful.auth.core.error.FacebookApiException;
import io.foodful.auth.core.error.InvalidTokenException;
import io.foodful.auth.core.service.facebook.FacebookAccessTokenResponse;
import io.foodful.auth.core.service.facebook.FacebookClient;
import io.foodful.auth.core.service.facebook.FacebookUserResponse;
import io.foodful.auth.core.service.message.LoginMessage;
import io.foodful.auth.core.service.message.LoginResult;
import io.foodful.auth.core.service.message.TokenResult;
import io.foodful.user.api.UserClient;
import io.foodful.user.api.dto.UserRequest;
import io.foodful.user.api.dto.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@Service
@Slf4j
public class LoginService {

    public enum LoginProvider {
        FACEBOOK
    }

    private TokenService tokenService;

    private FacebookClient facebookClient;
    private UserClient userClient;

    private String facebookClientId;
    private String facebookClientSecret;


    public LoginService(TokenService tokenService,
                        FacebookClient facebookClient,
                        UserClient userClient,
                        @Value("${foodful.auth.social.facebook.client-secret}") String facebookClientSecret,
                        @Value("${foodful.auth.social.facebook.client-id}") String facebookClientId) {
        this.tokenService = tokenService;
        this.facebookClient = facebookClient;
        this.userClient = userClient;
        this.facebookClientId = facebookClientId;
        this.facebookClientSecret = facebookClientSecret;
    }

    public LoginResult login(LoginMessage message) {

        FacebookAccessTokenResponse facebookAccessToken = facebookClient.getAccessToken(facebookClientId, message.redirect_uri, facebookClientSecret, message.code);
        FacebookUserResponse userInfo = getUserInfoFromFacebook(facebookAccessToken);

        UserResponse createdOrUpdatedUser = userClient.createUser(UserRequest.builder()
                .email(userInfo.email)
                .firstName(userInfo.firstName)
                .lastName(userInfo.lastName)
                .externalId(userInfo.id)
                .build());

        return tokenResultToLoginResult(tokenService.create(createdOrUpdatedUser.userId));
    }

    private FacebookUserResponse getUserInfoFromFacebook(FacebookAccessTokenResponse token) {
        String appSecretProof = sha256(token.accessToken, facebookClientSecret);

        try {
            return facebookClient.getUserData(getAuthorizationHeaderValue(token.accessToken), appSecretProof);
        } catch (FeignException exception) {
            if (exception.status() == 401) {
                throw new InvalidTokenException();
            } else {
                log.error("Facebook api exception occurred: ", exception);
                throw new FacebookApiException();
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
