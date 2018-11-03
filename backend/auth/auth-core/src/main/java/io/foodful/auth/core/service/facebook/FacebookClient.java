package io.foodful.auth.core.service.facebook;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "facebookClient", url = "${foodful.auth.social.facebook.url}")
public interface FacebookClient {

    @RequestMapping(value = "/v3.1/oauth/access_token", method = RequestMethod.GET)
    FacebookAccessTokenResponse getAccessToken(@RequestParam("client_id") String clientId,
                                               @RequestParam("redirect_uri") String redirectUri,
                                               @RequestParam("client_secret") String clientSecret,
                                               @RequestParam("code") String code);

    @RequestMapping(value = "/v3.1/me?fields=first_name,last_name,email", method = RequestMethod.GET)
    FacebookUserResponse getUserData(@RequestHeader("Authorization") String authorizationHeader, @RequestParam("appsecret_proof") String appSecretProof);

}