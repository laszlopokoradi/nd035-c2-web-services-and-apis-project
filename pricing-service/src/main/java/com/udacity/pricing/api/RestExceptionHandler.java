package com.udacity.pricing.api;


import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleException(Exception e) {
        return "An error occurred: " + e.getMessage();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleIllegalArgumentException(IllegalArgumentException e) {
        return "Invalid input: " + e.getMessage();
    }

    // Add more exception handlers as needed
}
