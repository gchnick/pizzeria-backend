package com.idforideas.pizzeria.exceptions;

import static java.time.LocalDateTime.now;

import javax.servlet.http.HttpServletRequest;

import com.idforideas.pizzeria.utils.Response;

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


@ControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class})
    @ResponseBody
    public Response noFountRequest(HttpServletRequest request, Exception exception) {
        return Response.builder()
            .timeStamp(now())
            .exception(exception.getClass().getSimpleName())
            .message(exception.getMessage())
            .path(request.getRequestURI())
            .build();
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
    public Response badRequest(HttpServletRequest request, Exception exception) {
        return Response.builder()
            .timeStamp(now())
            .exception(exception.getClass().getSimpleName())
            .message(exception.getMessage())
            .path(request.getRequestURI())
            .build();
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({ForbiddenRequestException.class})
    @ResponseBody
    public Response forbiddenRequest(HttpServletRequest request, Exception exception) {
        return Response.builder()
            .timeStamp(now())
            .exception(exception.getClass().getSimpleName())
            .message(exception.getMessage())
            .path(request.getRequestURI())
            .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({ConflictException.class})
    @ResponseBody
    public Response conflict(HttpServletRequest request, Exception exception) {
        return Response.builder()
            .timeStamp(now())
            .exception(exception.getClass().getSimpleName())
            .message(exception.getMessage())
            .path(request.getRequestURI())
            .build();
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
    public Response fatalErrorUnexpectedException(HttpServletRequest request, Exception exception) {
        return Response.builder()
            .timeStamp(now())
            .exception(exception.getClass().getSimpleName())
            .message(exception.getMessage())
            .path(request.getRequestURI())
            .build();
    }   
}
