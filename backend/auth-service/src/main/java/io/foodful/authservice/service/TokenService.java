package io.foodful.authservice.service;

import io.foodful.authservice.domain.AccessToken;
import io.foodful.authservice.domain.RefreshToken;
import io.foodful.authservice.domain.Token;
import io.foodful.authservice.error.InvalidTokenException;
import io.foodful.authservice.repository.AccessTokenRepository;
import io.foodful.authservice.repository.RefreshTokenRepository;
import io.foodful.authservice.service.message.TokenResult;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public TokenResult createTokensForUser(String userId) {
        AccessToken accessToken = createAccessTokenForUser(userId);
        accessToken = accessTokenRepository.save(accessToken);

        RefreshToken refreshToken = createRefreshTokenForUser(accessToken, userId);
        refreshToken = refreshTokenRepository.save(refreshToken);

        return TokenResult.builder()
                .accessToken(accessToken.getValue())
                .accessTokenExpires(accessToken.getExpirationDate())
                .refreshToken(refreshToken.getValue())
                .refreshTokenExpires(refreshToken.getExpirationDate())
                .build();
    }

    @Transactional
    public TokenResult renew(String refreshTokenId) {
        RefreshToken refreshToken = refreshTokenRepository.findById(refreshTokenId).orElse(null);
        if (refreshToken == null || !checkTokenValidity(refreshToken)) {
            throw new InvalidTokenException();
        }
        String userId = refreshToken.getUserId();
        invalidateAccessToken(refreshToken.getAccessToken().getValue());
        return createTokensForUser(userId);
    }

    private AccessToken createAccessTokenForUser(String userId) {
        AccessToken accessToken = new AccessToken();
        accessToken.setValue(generateRandomToken(accessTokenLength));
        accessToken.setExpirationDate(OffsetDateTime.now().plusMinutes(accessTokenExpiresInMinutes));
        accessToken.setUserId(userId);
        return accessToken;
    }

    private RefreshToken createRefreshTokenForUser(AccessToken accessToken, String userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setValue(generateRandomToken(refreshTokenLength));
        refreshToken.setExpirationDate(OffsetDateTime.now().plusHours(refreshTokenExpiresInHours));
        refreshToken.setUserId(userId);
        refreshToken.setAccessToken(accessToken);
        return refreshToken;
    }

    private void invalidateAccessToken(String accessToken) {
        refreshTokenRepository.deleteByAccessToken_Value(accessToken);
        accessTokenRepository.deleteById(accessToken);
    }

    private String generateRandomToken(int length) {
        return RandomStringUtils.random(length, 0, 0,
                true, true, null, new SecureRandom());
    }

    private boolean checkTokenValidity(Token token) {
        return OffsetDateTime.now().isBefore(token.getExpirationDate());
    }

}
