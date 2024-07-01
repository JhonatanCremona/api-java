package com.opc.client.repository;

import com.opc.client.models.SensorDigital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<SensorDigital, Integer> {
}
