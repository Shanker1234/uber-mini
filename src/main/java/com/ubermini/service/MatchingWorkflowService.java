package com.ubermini.service;

import java.time.Duration;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubermini.event.RideRequestedEvent;
import com.ubermini.model.Ride;
import com.ubermini.model.RideStatus;
import com.ubermini.repository.RideRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatchingWorkflowService {
	
	private final MatchingService matchingService;
	private final RideRepository rideRepository;
	private final NotificationService notificationService;
	private final DistributedLockService lockService;
	
	public  void processRideRequest(RideRequestedEvent event) {
		String driverId = matchingService.findNearestDriver(event.pickupLat(), event.pickupLng());
		if(driverId == null)
			return;
		
		String lockKey = "lock:driver:" + driverId;
		
		try {
			
			
			boolean locked = lockService.tryLock(lockKey, Duration.ofSeconds(30));
			
			if(!locked) {
				return;
			}
		
			
			Ride ride = rideRepository.findById(event.rideId()).orElseThrow();
			
			if(ride.getRideStatus() != RideStatus.REQUESTED) {
				return;
			}
			
			ride.setDriverId(UUID.fromString(driverId));
			ride.setRideStatus(RideStatus.MATCHED);
			
			Ride saved = rideRepository.save(ride);
			
			notificationService.notifyDriver(driverId, saved);
		} catch(Exception ex) {
			lockService.unlock(lockKey);
		}
		
		

	}

}
