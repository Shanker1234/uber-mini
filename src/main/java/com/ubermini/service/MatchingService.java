package com.ubermini.service;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubermini.model.RideStatus;
import com.ubermini.repository.RideRepository;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final DriverLocationService locationService;
    private final RideRepository rideRepository;

    public String findNearestDriver(
            double lat,
            double lng
    ) {

        List<String>  nearByDrivers = locationService.findNearByDrivers(
                        lat,
                        lng,
                        5
                );
        
        for(String driverId: nearByDrivers) {
        	boolean busy = rideRepository.existsByDriverIdAndRideStatusIn(UUID.fromString(driverId), List.of(
        																				RideStatus.MATCHED,
        																				RideStatus.DRIVER_EN_ROUTE,
        																				RideStatus.STARTED));
        	if(!busy) {
        		return driverId;
        	}
        }
        return null;
    }
}