package com.riznyk.fabware.geolocation.persistence.repository;

import com.riznyk.fabware.geolocation.persistence.entity.GpsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GpsRepository extends JpaRepository<GpsEntity, Long> {
}
