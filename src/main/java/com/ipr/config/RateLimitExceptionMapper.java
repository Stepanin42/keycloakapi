package com.ipr.config;

import io.quarkiverse.bucket4j.runtime.RateLimitException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

import java.util.Map;

public class RateLimitExceptionMapper implements ExceptionMapper<RateLimitException> {

    @Override
    public Response toResponse(RateLimitException ex) {
        return Response.status(429)
                .header("Retry-After", "60")
                .entity(Map.of("error", "Слишком много запросов, попробуйте позже"))
                .build();
    }
}
