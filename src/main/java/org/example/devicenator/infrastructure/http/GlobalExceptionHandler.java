package org.example.devicenator.infrastructure.http;

import org.example.devicenator.domain.device.DeviceAlreadyExists;
import org.example.devicenator.domain.device.DeviceNotFound;
import org.example.devicenator.domain.device.InvalidImei;
import org.example.devicenator.domain.user.InvalidEmail;
import org.example.devicenator.domain.user.UserAlreadyExists;
import org.example.devicenator.domain.user.UserNotFound;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({DeviceNotFound.class})
    protected ResponseEntity<Object> handleDeviceNotFound(Exception exception, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String responseBody =
                String.format("{\"error\": \"NON_EXISTING_DEVICE\", \"reason\": \"%s\"}", exception.getMessage());

        return handleExceptionInternal(exception, responseBody, headers, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({DeviceAlreadyExists.class})
    protected ResponseEntity<Object> handleDeviceAlreadyExists(Exception exception, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String responseBody =
                String.format("{\"error\": \"EXISTING_DEVICE\", \"reason\": \"%s\"}", exception.getMessage());

        return handleExceptionInternal(exception, responseBody, headers, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler({InvalidImei.class})
    protected ResponseEntity<Object> handleInvalidImei(Exception exception, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String responseBody =
                String.format("{\"error\": \"INVALID_IMEI\", \"reason\": \"%s\"}", exception.getMessage());

        return handleExceptionInternal(exception, responseBody, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({UserAlreadyExists.class})
    protected ResponseEntity<Object> handleUserAlreadyExists(Exception exception, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String responseBody =
                String.format("{\"error\": \"REGISTERED_USER\", \"reason\": \"%s\"}", exception.getMessage());

        return handleExceptionInternal(exception, responseBody, headers, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler({UserNotFound.class})
    protected ResponseEntity<Object> handleUserNotFound(Exception exception, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String responseBody =
            String.format("{\"error\": \"NOT_REGISTERED_USER\", \"reason\": \"%s\"}", exception.getMessage());

        return handleExceptionInternal(exception, responseBody, headers, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({InvalidEmail.class})
    protected ResponseEntity<Object> handleInvalidEmail(Exception exception, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String responseBody =
                String.format("{\"error\": \"INVALID_EMAIL\", \"reason\": \"%s\"}", exception.getMessage());

        return handleExceptionInternal(exception, responseBody, headers, HttpStatus.BAD_REQUEST, request);
    }
}
