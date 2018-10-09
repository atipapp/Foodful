package io.foodful.user.core.service;

import io.foodful.user.core.domain.User;
import io.foodful.user.core.errors.UserNotFoundException;
import io.foodful.user.core.repository.UserRepository;
import io.foodful.user.core.service.message.UserMessage;
import io.foodful.user.core.service.message.UserResult;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @SuppressWarnings("OptionalIsPresent")
    public UserResult createOrUpdate(UserMessage message) {
        Optional<User> alreadyRegisteredUser = repository.findByExternalId(message.externalId);

        User userToSave = alreadyRegisteredUser.isPresent() ?
                copyUserDataToUserFromMessage(alreadyRegisteredUser.get(), message) : createUserFromMessage(message);

        return convertUserToResult(repository.save(userToSave));
    }

    public UserResult get(String userId) {
        return convertUserToResult(repository
                .findById(userId).orElseThrow(UserNotFoundException::new));
    }

    private User createUserFromMessage(UserMessage message) {
        User userToSave;
        userToSave = User.builder()
                .email(message.email)
                .enabled(true)
                .firstName(message.firstName)
                .lastName(message.lastName)
                .roles(Collections.singletonList(User.Role.USER))
                .externalId(message.externalId)
                .build();
        return userToSave;
    }

    private User copyUserDataToUserFromMessage(User userToUpdate, UserMessage message) {
        userToUpdate.setEmail(message.email);
        userToUpdate.setFirstName(message.firstName);
        userToUpdate.setLastName(message.lastName);
        return userToUpdate;
    }

    private UserResult convertUserToResult(User user) {
        return UserResult.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userId(user.getId())
                .roles(user.getRoles().stream().map(Enum::name).collect(Collectors.toList()))
                .build();
    }
}
