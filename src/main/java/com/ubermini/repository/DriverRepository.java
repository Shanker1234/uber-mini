package com.ubermini.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ubermini.model.Driver;

public interface DriverRepository extends JpaRepository<Driver, UUID>{

}
