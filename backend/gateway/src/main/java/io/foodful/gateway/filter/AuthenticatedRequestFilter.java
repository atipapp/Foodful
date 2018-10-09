package io.foodful.gateway.filter;

import io.foodful.auth.api.AuthClient;
import io.foodful.auth.api.dto.InternalJwtCreationRequest;
import io.foodful.auth.api.dto.InternalJwtCreationResponse;
import io.foodful.auth.api.dto.TokenValidationRequest;
import io.foodful.auth.api.dto.TokenValidationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Optional;

@Component
public class AuthenticatedRequestFilter implements GatewayFilterFactory {

    private String tokenHeaderName;
    private String userDataHeaderName;

    private AuthClient authClient;

    public AuthenticatedRequestFilter(
            @Value("${io.foodful.auth.token-header}") String tokenHeaderName,
            @Value("${io.foodful.auth.user-data-header}") String userDataHeaderName,
            AuthClient authClient
    ) {
        this.authClient = authClient;
        this.tokenHeaderName = tokenHeaderName;
        this.userDataHeaderName = userDataHeaderName;
    }

    @Override
    public Object newConfig() {
        return new Object();
    }

    @Override
    public GatewayFilter apply(Object config) {
        return ((exchange, chain) ->
                this.getAccessToken(exchange.getRequest())
                        .flatMap(this::checkAccessToken)
                        .flatMap(userId -> this.putInternalJwtOnRequest(exchange.getRequest(), userId))
                        .flatMap(request -> chain.filter(exchange.mutate().request(request).build()))
                        .then()
        );
    }

    private Mono<String> getAccessToken(ServerHttpRequest request) {
        return Mono.justOrEmpty(Optional.ofNullable(request.getHeaders().getFirst(tokenHeaderName)))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED)));
    }

    private Mono<String> checkAccessToken(String accessToken) {
        TokenValidationRequest request = TokenValidationRequest.builder().access_token(accessToken).build();
        Mono<TokenValidationResponse> response = Mono.fromCallable(
                () -> this.authClient.validateAccessToken(request));
        response = response.subscribeOn(Schedulers.elastic());
        return response.filter(r -> r.isValid)
                .map(r -> r.userId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED)));
    }

    private Mono<ServerHttpRequest> putInternalJwtOnRequest(ServerHttpRequest request, String userId) {
        InternalJwtCreationRequest internalJwtCreationRequest = InternalJwtCreationRequest.builder().userId(userId).build();
        Mono<InternalJwtCreationResponse> response =
                Mono.fromCallable(() -> this.authClient.createInternalJwt(internalJwtCreationRequest));
        response = response.subscribeOn(Schedulers.elastic());
        return response.map(r -> request.mutate().header(this.userDataHeaderName, r.jwt).build());
    }

}
