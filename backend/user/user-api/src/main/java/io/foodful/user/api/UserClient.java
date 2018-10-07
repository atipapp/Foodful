package io.foodful.user.api;

import io.foodful.user.api.dto.UserRequest;
import io.foodful.user.api.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service-v1", url = "${foodful.clients.prefix}user-service-v1${foodful.clients.postfix}")
public interface UserClient {

    @PutMapping("/user")
    UserResponse createOrUpdateUser(@RequestBody UserRequest request);

    @GetMapping("/user/{userId}")
    UserResponse getUser(@PathVariable String userId);

}
