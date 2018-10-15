package io.foodful.auth.core.controller;

import io.foodful.auth.api.dto.InternalJwtCreationRequest;
import io.foodful.auth.api.dto.InternalJwtCreationResponse;
import io.foodful.auth.core.service.InternalTokensService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InternalJwtController {

    private InternalTokensService internalTokensService;

    public InternalJwtController(InternalTokensService internalTokensService) {
        this.internalTokensService = internalTokensService;
    }

    @PostMapping("/jwt")
    public InternalJwtCreationResponse createInternalJwt(InternalJwtCreationRequest request){
        String jwt = this.internalTokensService.createJwtForUser(request.userId);
        return InternalJwtCreationResponse.builder().jwt(jwt).build();
    }

}
