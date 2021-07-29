package com.riznyk.fabware.geolocation.web.controller.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@NoArgsConstructor
public class AreaCoordinatesModel {

  private String upperLeftX;
  private String upperLeftY;
  private String width;
  private String height;


  @JsonCreator
  public AreaCoordinatesModel(@JsonProperty("upperLeftX") String upperLeftX,
                              @JsonProperty("upperLeftY") String upperLeftY,
                              @JsonProperty("width") String width,
                              @JsonProperty("height") String height) {
    this.upperLeftX = upperLeftX;
    this.upperLeftY = upperLeftY;
    this.width = width;
    this.height = height;
  }

}
