package io.foodful.authservice.service;

import io.foodful.authservice.domain.AccessToken;
import io.foodful.authservice.domain.RefreshToken;
import io.foodful.authservice.repository.AccessTokenRepository;
import io.foodful.authservice.repository.RefreshTokenRepository;
import io.foodful.authservice.service.message.TokenResult;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.OffsetDateTime;

@Service
public class TokenService {

    private AccessTokenRepository accessTokenRepository;
    private RefreshTokenRepository refreshTokenRepository;

    private int accessTokenLength;
    private int accessTokenExpiresInMinutes;

    private int refreshTokenLength;
    private int refreshTokenExpiresInHours;

    public TokenService(AccessTokenRepository accessTokenRepository,
                        RefreshTokenRepository refreshTokenRepository,
                        @Value("${foodful.auth.token.access.length}") int accessTokenLength,
                        @Value("${foodful.auth.token.access.expires-in-minutes}") int accessTokenExpiresInMinutes,
                        @Value("${foodful.auth.token.refresh.length}") int refreshTokenLength,
                        @Value("${foodful.auth.token.refresh.expires-in-hours}") int refreshTokenExpiresInHours) {
        this.accessTokenRepository = accessTokenRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.accessTokenLength = accessTokenLength;
        this.accessTokenExpiresInMinutes = accessTokenExpiresInMinutes;
        this.refreshTokenLength = refreshTokenLength;
        this.refreshTokenExpiresInHours = refreshTokenExpiresInHours;
    }

    public TokenResult createTokensForUser(String userId) {
        AccessToken accessToken = this.createAccessTokenForUser(userId);
        accessToken = this.accessTokenRepository.save(accessToken);

        RefreshToken refreshToken = this.createRefreshTokenForUser(accessToken, userId);
        refreshToken = this.refreshTokenRepository.save(refreshToken);

        return TokenResult.builder()
                .accessToken(accessToken.getValue())
                .accessTokenExpires(accessToken.getExpirationDate())
                .refreshToken(refreshToken.getValue())
                .refreshTokenExpires(refreshToken.getExpirationDate())
                .build();
    }

    private AccessToken createAccessTokenForUser(String userId) {
        AccessToken accessToken = new AccessToken();
        accessToken.setValue(generateRandomToken(this.accessTokenLength));
        accessToken.setExpirationDate(OffsetDateTime.now().plusMinutes(accessTokenExpiresInMinutes));
        accessToken.setUserId(userId);
        return accessToken;
    }

    private RefreshToken createRefreshTokenForUser(AccessToken accessToken, String userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setValue(generateRandomToken(this.refreshTokenLength));
        refreshToken.setExpirationDate(OffsetDateTime.now().plusHours(refreshTokenExpiresInHours));
        refreshToken.setUserId(userId);
        refreshToken.setAccessToken(accessToken);
        return refreshToken;
    }

    private String generateRandomToken(int length) {
        return RandomStringUtils.random(length, 0, 0,
                true, true, null, new SecureRandom());
    }

}
