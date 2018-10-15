package io.foodful.user.core.repository;

import io.foodful.user.core.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findByExternalId(String externalId);

}
