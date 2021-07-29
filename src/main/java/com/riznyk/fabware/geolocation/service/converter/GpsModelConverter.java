package com.riznyk.fabware.geolocation.service.converter;

import com.riznyk.fabware.geolocation.persistence.entity.GpsEntity;
import com.riznyk.fabware.geolocation.web.controller.model.GpsCoordinatesModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GpsModelConverter implements Converter<GpsEntity, GpsCoordinatesModel> {

  @Override
  public GpsCoordinatesModel convert(final GpsEntity gpsEntity) {
    return GpsCoordinatesModel.builder()
        .latitude(gpsEntity.getLatitude())
        .longitude(gpsEntity.getLongitude())
        .build();
  }

}
