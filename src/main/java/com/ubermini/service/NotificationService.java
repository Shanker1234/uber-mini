package com.ubermini.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {
	
	private final SimpMessagingTemplate messagingTemplate;
	
	public void notifyDriver(String driverId, Object payload) {
		messagingTemplate.convertAndSend("/topic/driverId/" + driverId, payload);
	}
	
	public void notifyRider(String riderId, Object payload) {
		messagingTemplate.convertAndSend("/topic/riderId/" + riderId, payload);
	}

}
