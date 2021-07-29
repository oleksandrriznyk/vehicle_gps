package com.riznyk.fabware.geolocation.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.riznyk.fabware.geolocation.persistence.entity.GpsEntity;
import com.riznyk.fabware.geolocation.persistence.entity.VehicleEntity;
import com.riznyk.fabware.geolocation.persistence.repository.GpsRepository;
import com.riznyk.fabware.geolocation.persistence.repository.VehicleRepository;
import com.riznyk.fabware.geolocation.web.controller.it.configuration.SpringBootTestConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("local")
public class VehicleControllerIT extends SpringBootTestConfiguration {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private VehicleRepository vehicleRepository;
  @Autowired
  private GpsRepository gpsRepository;

  private Long vehicleId;


  @Before
  public void setUp() {
    final GpsEntity gpsEntity = GpsEntity.builder()
        .longitude(50.)
        .latitude(50.)
        .build();

    final GpsEntity gpsEntitySaved = gpsRepository.save(gpsEntity);

    VehicleEntity vehicleEntity = VehicleEntity.builder()
        .gpsEntity(gpsEntitySaved)
        .build();

    final VehicleEntity vehicleSaved = vehicleRepository.save(vehicleEntity);
    vehicleId = vehicleSaved.getId();
  }

  @Test
  @Transactional
  public void updateVehicleGpsCoordinates_success_whenInputIsValid() throws Exception {
    // arrange
    Double newLatitude = 1.1;
    Double newLongitude = 5.5;
    String requestBodyJson = "{\"latitude\":" + newLatitude + ", \"longitude\":" + newLongitude + "}";

    // act
    mockMvc.perform(put("/api/vehicles/" + vehicleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyJson))
        .andExpect(status().isAccepted());

    final VehicleEntity afterUpdate = vehicleRepository.findById(vehicleId).get();

    // assert
    assertEquals(newLatitude, afterUpdate.getGpsEntity().getLatitude());
    assertEquals(newLongitude, afterUpdate.getGpsEntity().getLongitude());
  }

  @Test
  @Transactional
  public void updateVehicleGpsCoordinates_throws_whenInputIsNotValid() throws Exception {
    // arrange
    Double newLatitude = 1.1;
    Double newLongitude = 5.5;
    String requestBodyJson = "{\"latitude\":" + newLatitude + ", \"longitude\":" + newLongitude + "}";
    Long randomNumber = new Random().nextLong() + 10;

    // act
    mockMvc.perform(put("/api/vehicles/" + randomNumber)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyJson))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Transactional
  public void findVehiclesInArea_success_whenInputIsValid() throws Exception {
    // arrange
    final String areaCoordinates = "{\"upperLeftX\":\"1.\",\"upperLeftY\":\"1.\",\"width\":\"100.\",\"height\":\"100.\"}";

    // act
    final MvcResult mvcResult = mockMvc.perform(get("/api/vehicles/")
                                                    .contentType(MediaType.APPLICATION_JSON)
    .param("areaCoordinates", areaCoordinates)
    .param("page", "0")
    .param("size", "30"))
        .andExpect(status().isOk()).andDo(print())
        .andReturn();

    @SuppressWarnings("unchecked")
    List<Object> responseContent = (List<Object>) objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Map.class)
        .get("content");

    @SuppressWarnings("unchecked")
    Map<String, Object> map = (Map<String, Object>) responseContent.get(0);
    final Integer responseVehicleId = (Integer) map.get("vehicleId");


    // assert
    assertEquals(vehicleId.longValue(), responseVehicleId.longValue());
  }

}