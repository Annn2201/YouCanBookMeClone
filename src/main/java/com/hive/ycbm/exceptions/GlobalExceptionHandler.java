package com.hive.ycbm.exceptions;

import com.hive.ycbm.YcbmApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(YcbmApplication.class);

    @ExceptionHandler(CustomException.class)
    public String handlerCustomException(CustomException e, Model model) {
        ErrorResponse errorResponse = new ErrorResponse(e.getHttpStatus().value(), e.getMessage(), new Date());
        ResponseEntity.status(e.getHttpStatus().value()).body(errorResponse);
        log.warn("Checked exception " + e.getMessage());
        model.addAttribute("status", errorResponse.getStatus());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handlerException(Exception e, Model model) {
        ErrorResponse errorResponse = new ErrorResponse(500, e.getMessage(), new Date());
        ResponseEntity.status(500).body(errorResponse);
        log.error("Unknown exception " + e.getMessage());
        model.addAttribute("status", errorResponse.getStatus());
        return "error";
    }
}
