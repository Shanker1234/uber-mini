package com.ubermini.event;

import java.util.UUID;

public record RideRequestedEvent(
		UUID rideId,
		UUID riderId,
		double pickupLat,
		double pickupLng
	) {

}
