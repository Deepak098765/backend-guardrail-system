package com.deepak.guardrail.exception;

public class RateLimitExceededException
        extends RuntimeException {

    public RateLimitExceededException(String message) {

        super(message);
    }
}