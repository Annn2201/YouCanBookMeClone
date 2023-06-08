package com.hive.ycbm.exceptions;

import com.hive.ycbm.YcbmApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(YcbmApplication.class);
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handlerCustomException(CustomException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getHttpStatus().value(), e.getMessage(), new Date());
        log.warn("Checked exception" + e.getMessage());
        return ResponseEntity.status(e.getHttpStatus().value()).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public String handlerException(Exception e){
        ErrorResponse errorResponse = new ErrorResponse(500, e.getMessage(), new Date());
        ResponseEntity.status(500).body(errorResponse);
        log.error("Unknown exception" + e.getMessage());
        return "redirect:/error";
    }
}
