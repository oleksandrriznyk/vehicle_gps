package com.riznyk.fabware.geolocation.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.riznyk.fabware.geolocation.exception.InvalidRequestBodyException;
import com.riznyk.fabware.geolocation.web.controller.model.AreaCoordinatesModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;

@ControllerAdvice
@RequiredArgsConstructor
public class ConfigWebDataBinder {

  private final ObjectMapper objectMapper;

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    addJsonEditor(binder, AreaCoordinatesModel.class);
  }

  private void addJsonEditor(WebDataBinder binder, Class<?> classObject) {
    binder.registerCustomEditor(classObject, json(classObject));
  }

  private PropertyEditorSupport json(Class<?> classValue) {
    return new PropertyEditorSupport() {
      @Override
      public void setAsText(String text) {
        try {
          setValue(objectMapper.readValue(text, classValue));
        }
        catch (JsonProcessingException e) {
          throw new InvalidRequestBodyException();
        }
      }
    };
  }

}
