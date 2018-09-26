package io.foodful.dinnerservice.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Dinner not found")
public class DinnerNotFoundException extends RuntimeException {
}