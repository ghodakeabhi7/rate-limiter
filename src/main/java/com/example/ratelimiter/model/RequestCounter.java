package com.example.ratelimiter.model;

public record RequestCounter(
        long windowStartMillis,
        int requestCount
) {
}