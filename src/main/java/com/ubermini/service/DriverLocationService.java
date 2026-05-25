package com.ubermini.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.ubermini.model.Ride;
import com.ubermini.model.RideStatus;
import com.ubermini.repository.RideRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DriverLocationService {
	
	private static final String DRIVER_GEO_KEY = "drivers:active";
	
	private final StringRedisTemplate redisTemplate;
	private final RideRepository rideRepository;
	private final NotificationService notificationService;
	
	public void updateDriverLocation(String driverId, double lat, double lng) {
		redisTemplate.opsForGeo().add(DRIVER_GEO_KEY, new Point(lng, lat), driverId);
		
		List<Ride> activeRides = rideRepository.findByDriverIdAndRideStatusIn(UUID.fromString(driverId), List.of(RideStatus.DRIVER_EN_ROUTE, RideStatus.STARTED));
		
		for(Ride ride: activeRides) {
			notificationService.notifyRider(ride.getRiderId().toString(), Map.of("type", "DRIVER_LOCATION",
																				"driverId",  driverId,
																				"lat" , lat,
																				"lng", lng));
		}
		
	}
	
	public List<String> findNearByDrivers(double lat, double lng, double radiusKm) {
		GeoResults<RedisGeoCommands.GeoLocation<String>> results = redisTemplate.opsForGeo().radius(DRIVER_GEO_KEY, 
												new Circle(new Point(lng, lat),
															new Distance(radiusKm,Metrics.KILOMETERS)));
		
		if(results == null) {
			return List.of();
		}
		
		return results.getContent().stream().map(r -> r.getContent().getName()).toList();
		
		
	}

}
