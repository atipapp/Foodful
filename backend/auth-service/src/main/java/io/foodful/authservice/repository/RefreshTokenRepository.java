package io.foodful.authservice.repository;

import io.foodful.authservice.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.time.OffsetDateTime;
import java.util.List;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

    void deleteByAccessToken_Value(String accessToken);

    List<RefreshToken> findRefreshTokenByExpirationDateBefore(OffsetDateTime now);
}
