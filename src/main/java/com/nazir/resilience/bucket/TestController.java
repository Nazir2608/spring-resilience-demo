package com.nazir.resilience.bucket;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TokenBucketService service;

    @GetMapping("/token")
    public String test() {

        if (!service.allowRequest()) {
            return "No Tokens Available";
        }
        return "Allowed. Remaining Tokens: " + service.remainingTokens();
    }
}