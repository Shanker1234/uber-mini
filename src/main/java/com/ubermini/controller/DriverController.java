package com.ubermini.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ubermini.dto.DirverLocationRequest;
import com.ubermini.service.DriverLocationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/drivers")
@RequiredArgsConstructor
public class DriverController {
	
	private final DriverLocationService driverLocationService;
	
	
	@PostMapping("/{driverId}/location")
	public void updateLocation(@PathVariable String driverId, @RequestBody DirverLocationRequest request) {
		driverLocationService.updateDriverLocation(driverId, request.lat(), request.lng());
	}
	
	
	@GetMapping("/nearBy")
	public Object nearByDrivers(@RequestParam double lat, @RequestParam double lng, @RequestParam double radiusKm) {
		return driverLocationService.findNearByDrivers(lat, lng, radiusKm);
	}
	
	

}
