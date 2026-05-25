package com.ubermini.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ubermini.model.Ride;
import com.ubermini.model.RideStatus;

public interface RideRepository extends JpaRepository<Ride, UUID>{
	
	//boolean existsByDriverIdAndStatusIn(UUID driverId, List<RideStatus> statuses);

	boolean existsByDriverIdAndRideStatusIn(UUID driverId, List<RideStatus> statuses);

	List<Ride> findByDriverIdAndRideStatusIn(UUID driverId, List<RideStatus> statuses);

}
