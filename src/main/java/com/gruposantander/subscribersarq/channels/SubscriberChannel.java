package com.gruposantander.subscribersarq.channels;


import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface SubscriberChannel {
	
	String INPUT = "subscribe";
	
	@Input(INPUT)
	SubscribableChannel subscribe();

}
