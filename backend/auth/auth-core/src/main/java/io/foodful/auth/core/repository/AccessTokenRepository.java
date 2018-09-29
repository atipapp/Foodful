package io.foodful.auth.core.repository;

import io.foodful.auth.core.domain.AccessToken;
import org.springframework.data.repository.CrudRepository;

public interface AccessTokenRepository extends CrudRepository<AccessToken, String> {
}
