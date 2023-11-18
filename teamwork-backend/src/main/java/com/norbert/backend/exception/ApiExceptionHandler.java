package com.norbert.backend.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {
    private final HttpServletRequest request;
    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Object> handleBadRequestException(RuntimeException exception){
        ApiException apiException = ApiException.builder()
                .error(HttpStatus.BAD_REQUEST.name())
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(apiException,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleBadRequestException(MethodArgumentNotValidException exception){
        ApiException apiException = ApiException.builder()
                .error(HttpStatus.BAD_REQUEST.name())
                .message(exception.getBody().getDetail())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(apiException,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleBadRequestException(MethodArgumentTypeMismatchException exception){
        ApiException apiException = ApiException.builder()
                .error(HttpStatus.BAD_REQUEST.name())
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(apiException,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MailSendingException.class})
    public ResponseEntity<Object> handleBadRequest(MailSendingException exception) {
        ApiException apiException = new ApiException(
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                exception.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(apiException, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
