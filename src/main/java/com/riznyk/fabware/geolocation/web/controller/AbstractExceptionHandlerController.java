package com.riznyk.fabware.geolocation.web.controller;

import com.riznyk.fabware.geolocation.exception.VehicleEntityNotFoundException;
import com.riznyk.fabware.geolocation.web.controller.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.riznyk.fabware.geolocation.web.controller.model.ErrorResponse.createCustomResponse;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
public class AbstractExceptionHandlerController {

  @ExceptionHandler(VehicleEntityNotFoundException.class)
  public ResponseEntity<ErrorResponse> exceptionHandler(Exception exception) {
    log.error("caught exception {}", exception.getMessage());

    return buildErrorResponseEntity(exception, "Not found vehicle with given id", BAD_REQUEST);
  }

  private ResponseEntity<ErrorResponse> buildErrorResponseEntity(Exception exception, String errorMessage, HttpStatus httpStatus) {
    ErrorResponse errorResponse = createCustomResponse(exception, errorMessage);
    return new ResponseEntity<>(errorResponse, httpStatus);
  }

}
