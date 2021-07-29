package com.riznyk.fabware.geolocation.web.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class VehicleModel {

  @JsonProperty(value = "gps")
  private GpsCoordinatesModel gpsCoordinatesModel;

}
