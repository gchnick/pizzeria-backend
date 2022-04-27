package com.idforideas.pizzeria.util;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.http.HttpStatus;

import lombok.Builder;


/**
 * Response general
 * @param timeStamp
 * @param statusCode
 * @param status
 * @param message
 * @param path
 * @param errors
 * @param data
 */
@JsonInclude(NON_NULL)
@Builder
public record Response(LocalDateTime timeStamp,
    int statusCode,
    HttpStatus status,
    String message,
    String path,
    Map<?, ?> errors,
    Map<?, ?> data) {    
}
