package com.nazir.resilience.bucket;


import org.springframework.stereotype.Service;

@Service
public class TokenBucketService {

    private final TokenBucket bucket = new TokenBucket(
                    5,      // max tokens
                    1,      // refill count
                    5000   // every 5 sec
            );

    public boolean allowRequest() {
        return bucket.allowRequest();
    }

    public int remainingTokens() {
        return bucket.getTokens();
    }
}