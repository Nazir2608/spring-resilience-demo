package com.nazir.resilience.controller;

import com.nazir.resilience.service.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final RateLimiterService rateLimiter;

    @GetMapping("/order")
    public ResponseEntity<String> placeOrder(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        System.out.println("IP=" + ip);
        if (!rateLimiter.allowRequest(ip,"order")) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Rate limit exceeded");
        }
        int remaining = rateLimiter.getRemainingRequests(ip,"order");
        return ResponseEntity.ok("Order placed. Remaining=" + remaining);
    }

    @GetMapping("/payments")
    public ResponseEntity<String> processPayment(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        if (!rateLimiter.allowRequest(ip, "payments")) {
            int remaining = rateLimiter.getRemainingRequests(ip, "payments");
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Rate limit exceeded for /payments. Remaining=" + remaining);
        }
        int remaining = rateLimiter.getRemainingRequests(ip, "payments");
        return ResponseEntity.ok("Payment processed. Remaining=" + remaining);
    }
}