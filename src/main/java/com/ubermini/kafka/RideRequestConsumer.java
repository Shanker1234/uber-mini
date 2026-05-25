package com.ubermini.kafka;

import org.springframework.kafka.annotation.BackOff;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;

import com.ubermini.event.RideRequestedEvent;
import com.ubermini.service.MatchingWorkflowService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RideRequestConsumer {
	
	private final MatchingWorkflowService workflowService;
	@RetryableTopic(
			attempts = "3",
			backOff = @BackOff(delay = 2000, multiplier = 2.0),
			dltTopicSuffix = "-dlt")
	@KafkaListener(
			topics= "ride-requests",
			groupId= "matching-group"
	)
	public void consume(RideRequestedEvent event) {
		workflowService.processRideRequest(event);
	}
	

}
