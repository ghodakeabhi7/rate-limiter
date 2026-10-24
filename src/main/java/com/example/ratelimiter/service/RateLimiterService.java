package com.example.ratelimiter.service;


import com.example.ratelimiter.model.RequestCounter;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class RateLimiterService {

    private static final int MAX_REQUESTS = 5;
    private static final long WINDOW_MILLIS = 60000;

    private final ConcurrentHashMap<String, RequestCounter> counters = new ConcurrentHashMap<>();


    public boolean allowRequest(String clientId) {

        long now = System.currentTimeMillis();
        AtomicBoolean allowed = new AtomicBoolean(false);

        counters.compute(clientId, (client, counter) -> {

            if (counter == null) {
                allowed.set(true);
                return new RequestCounter(now, 1);
            }

            long elapsed = now - counter.windowStartMillis();

            if (elapsed < WINDOW_MILLIS
                    && counter.requestCount() < MAX_REQUESTS) {
                allowed.set(true);
                return new RequestCounter(
                        counter.windowStartMillis(),
                        counter.requestCount() + 1
                );
            }

            if (elapsed >= WINDOW_MILLIS) {
                allowed.set(true);
                return new RequestCounter(now, 1);
            }

            return counter;
        });

        return allowed.get();
    }

}

