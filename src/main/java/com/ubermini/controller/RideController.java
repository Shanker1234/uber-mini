package com.ubermini.controller;

import com.ubermini.dto.RideRequestDto;
import com.ubermini.model.Ride;
import com.ubermini.service.RideService;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rides")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;

    @PostMapping("/request")
    public Ride requestRide(
            @RequestBody RideRequestDto request
    ) {

        return rideService.requestRide(request);
    }
    
    @PostMapping("/{rideId}/accept")
    public Ride acceptRide(@PathVariable UUID rideId) {
    	return rideService.acceptRide(rideId);
    	
    }
}