package com.ubermini.kafka;

import org.springframework.stereotype.Service;

import org.springframework.kafka.core.KafkaTemplate;

import com.ubermini.event.RideRequestedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RideEventProducer {

	private final KafkaTemplate<String, Object> kafkaTemplate;

	public void publishRideRequested(RideRequestedEvent event) {
		kafkaTemplate.send("ride-requests", event);
	}

}
