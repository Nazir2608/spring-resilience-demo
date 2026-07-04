package com.nazir.resilience.fixedwindow.service;

public record RequestInfo(
        int count,
        long windowStart
) {
}