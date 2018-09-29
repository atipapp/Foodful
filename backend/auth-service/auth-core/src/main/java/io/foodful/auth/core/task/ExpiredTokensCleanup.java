package io.foodful.auth.core.task;

import io.foodful.auth.core.service.TokenService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ExpiredTokensCleanup {

    private TokenService tokenService;

    public ExpiredTokensCleanup(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Scheduled(cron = "0 0/${foodful.auth.token.cleanup-frequency-in-minutes} * * * *")
    public void cleanExpiredTokens() {
        this.tokenService.cleanExpiredTokens();
    }

}
