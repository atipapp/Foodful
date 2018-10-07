package io.foodful.user.core.service;

import io.foodful.user.core.domain.User;
import io.foodful.user.core.errors.UserNotFoundException;
import io.foodful.user.core.repository.UserRepository;
import io.foodful.user.core.service.message.UserMessage;
import io.foodful.user.core.service.message.UserResult;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {

    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserResult create(UserMessage message) {
        User user = User.builder()
                .email(message.email)
                .enabled(true)
                .firstName(message.firstName)
                .lastName(message.lastName)
                .roles(Collections.singletonList(User.Role.USER))
                .build();

        return convertUserToResult(repository.save(user));
    }

    public UserResult get(String userId) {
        return convertUserToResult(repository
                .findById(userId).orElseThrow(UserNotFoundException::new));
    }

    private UserResult convertUserToResult(User user) {
        return UserResult.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userId(user.getId())
                .build();
    }
}
