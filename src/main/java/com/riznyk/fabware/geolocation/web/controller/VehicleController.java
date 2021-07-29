package com.riznyk.fabware.geolocation.web.controller;

import com.riznyk.fabware.geolocation.service.VehicleService;
import com.riznyk.fabware.geolocation.web.controller.model.AreaCoordinatesModel;
import com.riznyk.fabware.geolocation.web.controller.model.GpsCoordinatesModel;
import com.riznyk.fabware.geolocation.web.controller.model.VehicleModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/vehicles")
public class VehicleController extends AbstractExceptionHandlerController {

  private final VehicleService vehicleService;


  @ResponseStatus(value = OK)
  @PutMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
  public void updateVehicleGpsCoordinates(@PathVariable("id") Long id,
                                          @Valid @RequestBody GpsCoordinatesModel gpsCoordinatesModel) {
    log.info("Updating gps coordinates for vehicle with id {}", id);

    vehicleService.updateGpsCoordinates(id, gpsCoordinatesModel);
  }

  @GetMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<VehicleModel>> getVehiclesInArea(@PageableDefault Pageable pageable,
                                                              @RequestParam(name = "areaCoordinates")
                                                              @Valid AreaCoordinatesModel areaCoordinates) {

    log.info("Searching Vehicles in given area {}", areaCoordinates);

    final Page<VehicleModel> vehiclesInArea = vehicleService.findVehiclesInArea(areaCoordinates, pageable);

    return ResponseEntity.ok(vehiclesInArea);
  }

}
