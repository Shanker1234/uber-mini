package com.ubermini.dto;

import java.util.UUID;

public record RideRequestDto(
		UUID riderId,
		double pickupLat,
		double pickupLng,
		double dropLat,
		double dropLng
							) {

}
