package com.riznyk.fabware.geolocation.web.controller.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ErrorResponse {

  private final String errorClass;
  private final String errorMessage;


  @Builder(access = AccessLevel.PRIVATE)
  public ErrorResponse(String errorClass, String errorMessage) {
    this.errorClass = errorClass;
    this.errorMessage = errorMessage;
  }

  public static ErrorResponse createCustomResponse(Exception exception, String message) {
    return ErrorResponse.builder()
        .errorClass(exception.getClass().getSimpleName())
        .errorMessage(message)
        .build();
  }

}
