package com.riznyk.fabware.geolocation.service.converter;

import com.riznyk.fabware.geolocation.persistence.entity.GpsEntity;
import com.riznyk.fabware.geolocation.web.controller.model.GpsCoordinatesModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GpsEntityConverter implements Converter<GpsCoordinatesModel, GpsEntity> {

  @Override
  public GpsEntity convert(final GpsCoordinatesModel gpsCoordinatesModel) {
    return GpsEntity.builder()
        .latitude(gpsCoordinatesModel.getLatitude())
        .longitude(gpsCoordinatesModel.getLongitude())
        .build();
  }

}
