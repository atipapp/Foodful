package io.foodful.authservice.service.message;

import io.foodful.authservice.service.LoginService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginMessage {

    public String redirect_uri;
    public String code;
    public LoginService.LoginProvider provider;

}
