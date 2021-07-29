package com.riznyk.fabware.geolocation.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@Table(name = "vehicle")
@Builder(toBuilder = true)
public class VehicleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "gps_id")
  private GpsEntity gpsEntity;


  public VehicleEntity withGpsEntity(GpsEntity gpsEntity) {
    return this.toBuilder()
        .gpsEntity(gpsEntity)
        .build();
  }

}
