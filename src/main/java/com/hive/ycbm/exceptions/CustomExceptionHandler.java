package com.hive.ycbm.exceptions;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import jakarta.mail.MessagingException;

@ControllerAdvice
public class CustomExceptionHandler {
    @Secured("ROLE_ADMIN")
    @ExceptionHandler(UsernameNotFoundException.class)
    public ErrorResponse handlerUsernameNotFoundException(UsernameNotFoundException e) {
        return new ErrorResponse(404, e.getMessage());
    }

    @Secured("ROLE_ADMIN")
    @ExceptionHandler(MessagingException.class)
    public ErrorResponse handlerMessagingException(MessagingException e){
        return new ErrorResponse(400, e.getMessage());
    }

    @Secured("ROLE_ADMIN")
    @ExceptionHandler(Exception.class)
    public ErrorResponse handlerException(MessagingException e, HttpServletResponse response){
        return new ErrorResponse(response.getStatus(), e.getMessage());
    }
}
