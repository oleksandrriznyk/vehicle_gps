package com.riznyk.fabware.geolocation.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "gps")
@Builder(toBuilder = true)
public class GpsEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "longitude")
  private Double longitude;

  @Column(name = "latitude")
  private Double latitude;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "vehicle_id")
  private VehicleEntity vehicleEntity;


  public GpsEntity withLatitude(Double latitude) {
    return this.toBuilder()
        .latitude(latitude)
        .build();
  }

  public GpsEntity withLongitude(Double longitude) {
    return this.toBuilder()
        .longitude(longitude)
        .build();
  }

}
