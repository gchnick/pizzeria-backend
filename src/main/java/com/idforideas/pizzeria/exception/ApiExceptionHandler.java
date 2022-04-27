package com.idforideas.pizzeria.exception;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import javax.servlet.http.HttpServletRequest;
import javax.validation.UnexpectedTypeException;

import com.idforideas.pizzeria.util.Response;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@ControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler({NotFoundException.class})
    @ResponseBody
    public Response noFountRequest(HttpServletRequest request, Exception exception) {
        return Response.builder()
            .timeStamp(now())
            .message(exception.getMessage())
            .path(request.getRequestURI())
            .status(NOT_FOUND)
            .statusCode(NOT_FOUND.value())
            .build();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler({
        BadRequestException.class,
        DuplicateKeyException.class,
        IllegalArgumentException.class,
        UnexpectedTypeException.class,
        HttpRequestMethodNotSupportedException.class,
        MissingRequestHeaderException.class,
        MissingServletRequestParameterException.class,
        MethodArgumentTypeMismatchException.class,
        HttpMessageNotReadableException.class
    })
    @ResponseBody
    public Response badRequest(HttpServletRequest request, Exception exception) {

        return Response.builder()
            .timeStamp(now())
            .message(exception.getMessage())
            .status(BAD_REQUEST)
            .statusCode(BAD_REQUEST.value())
            .path(request.getRequestURI())
            .build();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler({
        BindException.class,
        MethodArgumentNotValidException.class,
    })
    @ResponseBody
    public Response handleValidationExceptions(HttpServletRequest request, BindException exception) {
        return Response.builder()
            .timeStamp(now())
            .errors(exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(e -> new Errors(((FieldError) e).getField(), e.getDefaultMessage()))
                .collect(Collectors.toMap(Errors::fielName, Errors::errorMessage)))
            .status(BAD_REQUEST)
            .statusCode(BAD_REQUEST.value())
            .path(request.getRequestURI())
            .build();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler({MissingServletRequestPartException.class})
    @ResponseBody
    public Response multipartException(HttpServletRequest request, MissingServletRequestPartException exception) {
        return Response.builder()
            .timeStamp(now())
            .errors(of(exception.getRequestPartName(), exception.getMessage()))
            .status(BAD_REQUEST)
            .statusCode(BAD_REQUEST.value())
            .path(request.getRequestURI())
            .build();
    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler({ForbiddenRequestException.class})
    @ResponseBody
    public Response forbiddenRequest(HttpServletRequest request, Exception exception) {
        return Response.builder()
            .timeStamp(now())
            .errors(of("exception", exception.getClass().getSimpleName()))
            .message(exception.getMessage())
            .status(FORBIDDEN)
            .statusCode(FORBIDDEN.value())
            .path(request.getRequestURI())
            .build();
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler({ConflictException.class})
    @ResponseBody
    public Response conflict(HttpServletRequest request, Exception exception) {
        return Response.builder()
            .timeStamp(now())
            .errors(of("exception", exception.getClass().getSimpleName()))
            .message(exception.getMessage())
            .status(CONFLICT)
            .statusCode(CONFLICT.value())
            .path(request.getRequestURI())
            .build();
    }

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler({
        UnauthorizedException.class,
        AccessDeniedException.class
    })
    public void unauthorized(HttpServletRequest request, Exception exception) {
        // Empty
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Response fatalErrorUnexpectedException(HttpServletRequest request, Exception exception) {
        return Response.builder()
            .timeStamp(now())
            .errors(of("exception", exception.getClass().getSimpleName()))
            .message(exception.getMessage())
            .status(INTERNAL_SERVER_ERROR)
            .statusCode(INTERNAL_SERVER_ERROR.value())
            .path(request.getRequestURI())
            .build();
    }
    
    private record Errors (String fielName, String errorMessage){};
}
