package com.riznyk.fabware.geolocation.web.controller.model.validation.validator;

import com.riznyk.fabware.geolocation.web.controller.model.validation.constraint.Longitude;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LongitudeValidator implements ConstraintValidator<Longitude, Double> {

  @Override
  public boolean isValid(final Double value, final ConstraintValidatorContext context) {
    return value > 180 || value < -180;
  }

  @Override
  public void initialize(final Longitude constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

}
