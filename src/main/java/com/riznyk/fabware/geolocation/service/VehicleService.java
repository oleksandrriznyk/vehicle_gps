package com.riznyk.fabware.geolocation.service;

import com.riznyk.fabware.geolocation.persistence.entity.GpsEntity;
import com.riznyk.fabware.geolocation.persistence.entity.VehicleEntity;
import com.riznyk.fabware.geolocation.persistence.repository.VehicleRepository;
import com.riznyk.fabware.geolocation.service.converter.GpsEntityConverter;
import com.riznyk.fabware.geolocation.service.converter.VehicleModelConverter;
import com.riznyk.fabware.geolocation.web.controller.model.AreaCoordinatesModel;
import com.riznyk.fabware.geolocation.web.controller.model.GpsCoordinatesModel;
import com.riznyk.fabware.geolocation.web.controller.model.VehicleModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.awt.geom.Rectangle2D;
import java.util.List;

import static java.lang.Double.parseDouble;
import static java.util.stream.Collectors.toUnmodifiableList;

@Service
@RequiredArgsConstructor
public class VehicleService {

  private final VehicleRepository vehicleRepository;
  private final GpsEntityConverter gpsEntityConverter;
  private final VehicleModelConverter vehicleModelConverter;


  public VehicleEntity updateGpsCoordinates(Long vehicleId, GpsCoordinatesModel gpsCoordinatesModel) {
    final VehicleEntity vehicleEntity = vehicleRepository.findById(vehicleId)
        .orElseThrow(EntityNotFoundException::new);

    final GpsEntity gpsEntity = gpsEntityConverter.convert(gpsCoordinatesModel);
    final GpsEntity withVehicleEntity = gpsEntity.withVehicleEntity(vehicleEntity);

    final VehicleEntity vehicleWithGps = vehicleEntity.withGpsEntity(withVehicleEntity);

    return vehicleRepository.save(vehicleWithGps);
  }

  public Page<VehicleModel> findVehiclesInArea(final AreaCoordinatesModel areaCoordinates, final Pageable pageable) {
    final Page<VehicleEntity> vehicleEntities = vehicleRepository.findAll(pageable);

    final Rectangle2D.Double area = getArea(areaCoordinates);

    final List<VehicleModel> vehicleModels = getVehicleModels(vehicleEntities, area);

    return new PageImpl<>(vehicleModels, pageable, vehicleEntities.getTotalElements());
  }

  private List<VehicleModel> getVehicleModels(final Page<VehicleEntity> vehicleEntities, final Rectangle2D.Double area) {
    return vehicleEntities.stream()
        .filter(vehicle -> area.contains(vehicle.getGpsEntity().getLatitude(), vehicle.getGpsEntity().getLongitude()))
        .map(vehicleModelConverter::convert)
        .collect(toUnmodifiableList());
  }

  private Rectangle2D.Double getArea(final AreaCoordinatesModel areaCoordinates) {
    return new Rectangle2D.Double(parseDouble(areaCoordinates.getUpperLeftX()),
                                  parseDouble(areaCoordinates.getUpperLeftY()),
                                  parseDouble(areaCoordinates.getWidth()),
                                  parseDouble(areaCoordinates.getHeight()));
  }

}
