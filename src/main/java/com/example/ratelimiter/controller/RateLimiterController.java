package com.example.ratelimiter.controller;

import com.example.ratelimiter.constant.Constant;
import com.example.ratelimiter.service.RateLimiterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/rate-limiter")
public class RateLimiterController {

    private final RateLimiterService rateLimiterService;

    public RateLimiterController(RateLimiterService rateLimiterService){
        this.rateLimiterService = rateLimiterService;

    }

    @GetMapping("/check-request-allow")
    public ResponseEntity<String> isRequestAllowed(@RequestHeader("client-id") String clinetId){
        if(rateLimiterService.allowRequest(clinetId)){
            return ResponseEntity.status(Constant.OK).body("Ok");
        }
        return ResponseEntity.status(Constant.TOO_MANY_REQUEST).body("Too Many Requests");
    }

}

