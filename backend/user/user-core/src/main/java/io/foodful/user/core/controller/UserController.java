package io.foodful.user.core.controller;

import io.foodful.user.api.dto.UserRequest;
import io.foodful.user.api.dto.UserResponse;
import io.foodful.user.core.service.UserService;
import io.foodful.user.core.service.message.UserResult;
import org.springframework.web.bind.annotation.*;

import static io.foodful.user.core.util.DTOConverter.userRequestToMessage;
import static io.foodful.user.core.util.DTOConverter.userResultToResponse;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("")
    public UserResponse createUser(@RequestBody UserRequest request) {
        UserResult result = service.create(userRequestToMessage(request));
        return userResultToResponse(result);
    }

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable String userId) {
        UserResult result = service.get(userId);
        return userResultToResponse(result);
    }

}
