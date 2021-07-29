package com.riznyk.fabware.geolocation.service.converter;

import com.riznyk.fabware.geolocation.persistence.entity.VehicleEntity;
import com.riznyk.fabware.geolocation.web.controller.model.VehicleModel;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VehicleModelConverter implements Converter<VehicleEntity, VehicleModel> {

  private final GpsModelConverter gpsModelConverter;

  @Override
  public VehicleModel convert(final VehicleEntity vehicleEntity) {
    return VehicleModel.builder()
        .gpsCoordinatesModel(gpsModelConverter.convert(vehicleEntity.getGpsEntity()))
        .build();
  }

}
