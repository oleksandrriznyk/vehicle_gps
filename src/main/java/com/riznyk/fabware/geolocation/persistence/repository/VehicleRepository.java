package com.riznyk.fabware.geolocation.persistence.repository;

import com.riznyk.fabware.geolocation.persistence.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {
}
