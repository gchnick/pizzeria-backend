package com.idforideas.pizzeria.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import app.nikoshop.poultry.payload.response.ErrorResponse;

//@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"} , maxAge = 3600)
@ControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class})
    @ResponseBody
    public ErrorResponse noFountRequest(HttpServletRequest request, Exception exception) {
        return new ErrorResponse(exception, request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
        BadRequestException.class,
        DuplicateKeyException.class,
        IllegalArgumentException.class,
        HttpRequestMethodNotSupportedException.class,
        MissingRequestHeaderException.class,
        MissingServletRequestParameterException.class,
        MethodArgumentTypeMismatchException.class,
        HttpMessageNotReadableException.class
    })
    @ResponseBody
    public ErrorResponse badRequest(HttpServletRequest request, Exception exception) {
        return new ErrorResponse(exception, request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({ForbiddenRequestException.class})
    @ResponseBody
    public ErrorResponse forbiddenRequest(HttpServletRequest request, Exception exception) {
        return new ErrorResponse(exception, request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({ConflictException.class})
    @ResponseBody
    public ErrorResponse conflict(HttpServletRequest request, Exception exception) {
        return new ErrorResponse(exception, request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({
        UnauthorizedException.class,
        AccessDeniedException.class
    })
    public void unauthorized(HttpServletRequest request, Exception exception) {
        // Empty
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ErrorResponse fatalErrorUnexpectedException(HttpServletRequest request, Exception exception) {
        return new ErrorResponse(exception, request.getRequestURI());
    }   
}
