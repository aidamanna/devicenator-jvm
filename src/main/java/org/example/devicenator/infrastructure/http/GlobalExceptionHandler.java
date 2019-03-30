package org.example.devicenator.infrastructure.http;

import org.example.devicenator.domain.device.DeviceNotFound;
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
    protected ResponseEntity<Object> handle(Exception exception, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String responseBody = "{\"error\": \"NON_EXISTING_DEVICE\", \"reason\": \"The device is not registered\"}";

        return handleExceptionInternal(exception, responseBody, headers, HttpStatus.BAD_REQUEST, request);
    }
}
