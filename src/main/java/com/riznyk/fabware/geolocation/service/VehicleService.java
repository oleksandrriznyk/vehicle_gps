package com.riznyk.fabware.geolocation.service;

import com.riznyk.fabware.geolocation.exception.VehicleEntityNotFoundException;
import com.riznyk.fabware.geolocation.persistence.entity.GpsEntity;
import com.riznyk.fabware.geolocation.persistence.entity.VehicleEntity;
import com.riznyk.fabware.geolocation.persistence.repository.GpsRepository;
import com.riznyk.fabware.geolocation.persistence.repository.VehicleRepository;
import com.riznyk.fabware.geolocation.service.converter.AreaCoordinatesModelConverter;
import com.riznyk.fabware.geolocation.service.converter.VehicleModelConverter;
import com.riznyk.fabware.geolocation.web.controller.model.AreaCoordinatesModel;
import com.riznyk.fabware.geolocation.web.controller.model.VehicleModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.awt.geom.Rectangle2D;
import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

@Service
@RequiredArgsConstructor
public class VehicleService {

  private final GpsRepository gpsRepository;
  private final VehicleRepository vehicleRepository;
  private final VehicleModelConverter vehicleModelConverter;
  private final AreaCoordinatesModelConverter areaCoordinatesModelConverter;


  public void updateGpsCoordinates(Long vehicleId, Double latitude, Double longitude) {
    final VehicleEntity vehicleEntity = vehicleRepository.findById(vehicleId)
        .orElseThrow(VehicleEntityNotFoundException::new);

    final GpsEntity updatedGps = updateGps(latitude, longitude, vehicleEntity);

    final GpsEntity gpsEntity = gpsRepository.save(updatedGps);
    final VehicleEntity vehicleWithGps = vehicleEntity.withGpsEntity(gpsEntity);

    vehicleRepository.save(vehicleWithGps);
  }

  public Page<VehicleModel> findVehiclesInArea(final AreaCoordinatesModel areaCoordinates, final Pageable pageable) {
    final Page<VehicleEntity> vehicleEntities = vehicleRepository.findAll(pageable);

    final Rectangle2D.Double area = areaCoordinatesModelConverter.convert(areaCoordinates);
    final List<VehicleModel> vehicleModels = getVehicleModelsInArea(vehicleEntities, area);

    return new PageImpl<>(vehicleModels, pageable, vehicleEntities.getTotalElements());
  }

  private List<VehicleModel> getVehicleModelsInArea(final Page<VehicleEntity> vehicleEntities, final Rectangle2D.Double area) {
    return vehicleEntities.stream()
        .filter(vehicle -> area.contains(vehicle.getGpsEntity().getLatitude(), vehicle.getGpsEntity().getLongitude()))
        .map(vehicleModelConverter::convert)
        .collect(toUnmodifiableList());
  }

  private GpsEntity updateGps(final Double latitude, final Double longitude, final VehicleEntity vehicleEntity) {
    return vehicleEntity.getGpsEntity()
        .withLatitude(latitude)
        .withLongitude(longitude);
  }

}
