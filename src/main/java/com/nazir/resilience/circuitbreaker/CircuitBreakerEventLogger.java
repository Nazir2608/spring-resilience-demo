package com.nazir.resilience.circuitbreaker;

import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnStateTransitionEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CircuitBreakerEventLogger {

    @EventListener
    public void onStateTransition(CircuitBreakerOnStateTransitionEvent event) {
        System.out.println("State changed: " + event.getStateTransition());
    }
}