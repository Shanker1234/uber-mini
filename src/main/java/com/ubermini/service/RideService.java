package com.ubermini.service;

import com.ubermini.dto.RideRequestDto;
import com.ubermini.event.RideRequestedEvent;
import com.ubermini.kafka.RideEventProducer;
import com.ubermini.model.Ride;
import com.ubermini.model.RideStatus;
import com.ubermini.repository.RideRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RideService {

    private final RideRepository rideRepository;
    private final MatchingService matchingService;
    private final NotificationService notificationService;
    private final RideEventProducer producer;

    public Ride requestRide(
            RideRequestDto request
    ) {

        Ride ride = Ride.builder()
                .id(UUID.randomUUID())
                .riderId(request.riderId())
                .rideStatus(RideStatus.REQUESTED)
                .pickupLat(request.pickupLat())
                .pickupLng(request.pickupLng())
                .dropLat(request.dropLat())
                .dropLng(request.dropLng())
                .requestedAt(LocalDateTime.now())
                .build();

        Ride saved = rideRepository.save(ride);
        
        producer.publishRideRequested(new RideRequestedEvent(
        								saved.getId(),
        								saved.getRiderId(),
        								saved.getPickupLat(),
        								saved.getPickupLng()));
        
        return saved;
    }
    
    @Transactional
    public Ride acceptRide(UUID rideId) {
    	Ride ride = rideRepository.findById(rideId).orElseThrow();
    	
    	ride.setRideStatus(RideStatus.DRIVER_EN_ROUTE);
    	Ride saved = rideRepository.save(ride);
    	
    	notificationService.notifyRider(ride.getRiderId().toString(), saved);
    	return saved;
    	
    }
    
    @Transactional
    public Ride startRide(UUID rideId) {
    	Ride ride = rideRepository.findById(rideId).orElseThrow();
    	ride.setRideStatus(RideStatus.STARTED);
    	
    	Ride saved = rideRepository.save(ride);
    	
    	notificationService.notifyRider(ride.getRiderId().toString(), saved);
    	return saved;
    	
    }
    
    @Transactional
    public Ride completeRide(UUID rideId) {

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow();

        ride.setRideStatus(RideStatus.COMPLETED);

        Ride saved = rideRepository.save(ride);

        notificationService.notifyRider(
                ride.getRiderId().toString(),
                saved
        );

        return saved;
    }
}