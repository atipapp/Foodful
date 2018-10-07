package io.foodful.auth.core.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE, reason = "Facebook api exception occurred")
public class FacebookApiException extends RuntimeException {
}
