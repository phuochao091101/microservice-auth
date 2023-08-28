package com.example.authservice.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import java.time.Instant;


public class ServiceException extends ErrorResponseException {
    public ServiceException(String message) {
        super(HttpStatus.SERVICE_UNAVAILABLE, asProblemDetail(message), null);
    }
    private static ProblemDetail asProblemDetail(String message) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.SERVICE_UNAVAILABLE, message);
        problemDetail.setTitle("Service Unavailable");
        problemDetail.setProperty("errorCategory", "Generic");
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}
