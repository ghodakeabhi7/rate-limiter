package com.example.ratelimiter.filter;

import com.example.ratelimiter.constant.Constant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String clientId = request.getHeader("client-id");

        if(clientId==null || clientId.isBlank()){
            response.setStatus(Constant.BAD_REQUEST);
            response.setContentType("text/plain");
            response.getWriter().write("client-id header is required");
            return;
        }

        // Header is valid: continue to the next filter or controller
        filterChain.doFilter(request, response);

    }
}
