package com.ubermini.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="rides")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ride {
	
	
	@Id
	private UUID id;
	
	private UUID riderId;
	
	private UUID driverId;
	
	
	@Enumerated(EnumType.STRING)
	private RideStatus rideStatus;
	
	private double pickupLat;
	private double pickupLng;
	
	private double dropLat;
	private double dropLng;
	
	private LocalDateTime requestedAt;
	

}
