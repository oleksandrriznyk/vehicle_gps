package com.riznyk.fabware.geolocation.web.controller.model.validation.validator;

import com.riznyk.fabware.geolocation.web.controller.model.validation.constraint.Latitude;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LatitudeValidator implements ConstraintValidator<Latitude, Double> {

  @Override
  public boolean isValid(final Double value, final ConstraintValidatorContext context) {
    return value > 90 || value < -90;
  }

  @Override
  public void initialize(final Latitude constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

}
