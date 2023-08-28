package com.study.apigateway.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class HandelException extends ResponseEntityExceptionHandler {
}
