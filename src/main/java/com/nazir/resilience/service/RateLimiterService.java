package com.nazir.resilience.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterService {

    private final Map<String, RequestInfo> requests = new ConcurrentHashMap<>();
    private static final Map<String, Integer> LIMITS = Map.of("orders", 5, "payments", 2);

    private static final long WINDOW_SIZE = 60_000; // 1 minute

    private String key(String clientId, String endpoint) {
        return clientId + ":" + endpoint;
    }

    public boolean allowRequest(String clientId, String endpoint) {
        long now = System.currentTimeMillis();
        String compositeKey = key(clientId, endpoint);
        RequestInfo info = requests.computeIfAbsent(compositeKey, k -> new RequestInfo(0, now));

        synchronized (info) {

            if (now - info.windowStart() > WINDOW_SIZE) {
                requests.put(compositeKey, new RequestInfo(1, now));
                return true;
            }
            Integer limit = LIMITS.getOrDefault(endpoint, 5);
            if (info.count() < limit) {
                requests.put(compositeKey, new RequestInfo(info.count() + 1, info.windowStart()));
                return true;
            }

            return false;
        }
    }

    public int getRemainingRequests(String clientId, String endpoint) {
        long now = System.currentTimeMillis();
        String compositeKey = key(clientId, endpoint);
        RequestInfo info = requests.get(compositeKey);

        int limit = LIMITS.getOrDefault(endpoint, 5);
        if (info == null || now - info.windowStart() > WINDOW_SIZE) {
            return limit;
        }
        return Math.max(0, limit - info.count());
    }
}