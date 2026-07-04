package com.nazir.resilience.bucket;

public class TokenBucket {

    private final int capacity;
    private final int refillTokens;
    private final long refillIntervalMs;

    private int tokens;
    private long lastRefillTime;

    public TokenBucket(int capacity, int refillTokens, long refillIntervalMs) {

        this.capacity = capacity;
        this.refillTokens = refillTokens;
        this.refillIntervalMs = refillIntervalMs;

        this.tokens = capacity;
        this.lastRefillTime = System.currentTimeMillis();
    }

    public synchronized boolean allowRequest() {

        refill();

        if (tokens > 0) {
            tokens--;
            return true;
        }

        return false;
    }

    private void refill() {

        long now = System.currentTimeMillis();
        long elapsed = now - lastRefillTime;
        if (elapsed >= refillIntervalMs) {
            long intervals = elapsed / refillIntervalMs;
            int newTokens = (int) (intervals * refillTokens);
            tokens = Math.min(capacity, tokens + newTokens);
            lastRefillTime = now;
        }
    }

    public int getTokens() {
        return tokens;
    }
}