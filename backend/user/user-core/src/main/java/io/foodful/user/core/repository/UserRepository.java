package io.foodful.user.core.repository;

import io.foodful.user.core.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}
