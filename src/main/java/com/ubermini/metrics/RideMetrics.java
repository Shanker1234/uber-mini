package com.ubermini.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RideMetrics {

    private final MeterRegistry meterRegistry;

    private Counter rideRequests;
    private Counter ridesAccepted;

    @PostConstruct
    public void init() {

        rideRequests = Counter.builder("ride_requests_total")
                .description("Total ride requests")
                .register(meterRegistry);

        ridesAccepted = Counter.builder("rides_accepted_total")
                .description("Total rides accepted")
                .register(meterRegistry);
    }

    public void incrementRideRequests() {
        rideRequests.increment();
    }

    public void incrementRidesAccepted() {
        ridesAccepted.increment();
    }
}