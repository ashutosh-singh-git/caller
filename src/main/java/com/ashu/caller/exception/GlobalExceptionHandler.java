package com.ashu.caller.exception;

import com.ashu.caller.model.Response;
import com.ashu.caller.util.Utility;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        String message = getError(bindingResult);
        return new ResponseEntity<>(Utility.failureResponse(message), HttpStatus.BAD_REQUEST);
    }

    private String getError(BindingResult bindingResult) {
        StringBuilder stringBuilder = new StringBuilder();
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(fieldError -> {
                String msg = fieldError.getField() + " : " + fieldError.getDefaultMessage() + ", ";
                stringBuilder.append(msg);
            });
        }
        return stringBuilder.toString();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Response<String>> handleException(DataIntegrityViolationException e) {
        return new ResponseEntity<>(Utility.failureResponse("Data Integrity Violation"), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(CallerException.class)
    public ResponseEntity<Response> handleException(CallerException e) {
        return new ResponseEntity<>(Utility.failureResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Response> handleException(Exception ex) {
        return new ResponseEntity<>(Utility.failureResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
