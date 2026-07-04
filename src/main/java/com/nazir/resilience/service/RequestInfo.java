package com.nazir.resilience.service;

public record RequestInfo(
        int count,
        long windowStart
) {
}