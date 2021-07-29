package com.riznyk.fabware.geolocation.web.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.riznyk.fabware.geolocation.web.controller.model.validation.constraint.Latitude;
import com.riznyk.fabware.geolocation.web.controller.model.validation.constraint.Longitude;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class GpsCoordinatesModel {

  @NotNull
  @Latitude
  @JsonProperty
  private final Double latitude;

  @NotNull
  @Longitude
  @JsonProperty
  private final Double longitude;

}
