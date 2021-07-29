package com.riznyk.fabware.geolocation.web.controller.model.validation.constraint;

import com.riznyk.fabware.geolocation.web.controller.model.validation.validator.LatitudeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = LatitudeValidator.class)
@Documented
public @interface Longitude {

  String message() default "Invalid Longitude value";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
