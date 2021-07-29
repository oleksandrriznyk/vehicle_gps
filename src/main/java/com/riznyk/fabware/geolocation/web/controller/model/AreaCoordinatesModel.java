package com.riznyk.fabware.geolocation.web.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class AreaCoordinatesModel {

  private Double upperLeftX;
  private Double upperLeftY;
  private Double width;
  private Double height;

}
