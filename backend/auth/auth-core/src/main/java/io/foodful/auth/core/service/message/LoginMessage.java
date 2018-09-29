package io.foodful.auth.core.service.message;

import io.foodful.auth.core.service.LoginService;
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
