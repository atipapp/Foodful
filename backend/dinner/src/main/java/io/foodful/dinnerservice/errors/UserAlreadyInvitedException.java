package io.foodful.dinnerservice.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "User already invited")
public class UserAlreadyInvitedException extends RuntimeException {
}
