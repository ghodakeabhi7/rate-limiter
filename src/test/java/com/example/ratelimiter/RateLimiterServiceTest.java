package com.example.ratelimiter;

import com.example.ratelimiter.model.RequestCounter;
import com.example.ratelimiter.service.RateLimiterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RateLimiterServiceTest {

    private RateLimiterService service;

    @BeforeEach
    void setUp(){
        service = new RateLimiterService();
    }

    @Test
    void firstRequestShouldBeAllowed(){
        boolean result = service.allowRequest("client-1");
        assertTrue(result);
    }

    @Test
    void firstFiveRequestShouldAllowed(){
        boolean result;
        for(int i=0;i<5;i++){
            result = service.allowRequest("client-1");
            assertTrue(result);
        }
    }

    @Test
    void sixthRequestShouldReject(){
        boolean result;
        for(int i=0;i<5;i++){
            result = service.allowRequest("client-1");
            assertTrue(result);
        }
        result = service.allowRequest("client-1");
        assertFalse(result);
    }


    @Test
    void separateCLientRequest(){
        boolean result1;
        boolean result2;
        for(int i=0;i<5;i++){
            result1 = service.allowRequest("client-1");
            assertTrue(result1);
        }

        result1 = service.allowRequest("client-1");
        assertFalse(result1);

        result2 = service.allowRequest("client-2");
        assertTrue(result2);


    }

    @Test
    void shouldVerifyWindowExpiry() throws InterruptedException {
        boolean result1;
        for(int i=0;i<5;i++){
            result1 = service.allowRequest("client-1");
            assertTrue(result1);
        }
        Thread.sleep(60000);
        result1 = service.allowRequest("client-1");
        assertTrue(result1);
    }


}

