package com.gruposantander.subscribersarq.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.gruposantander.subscribersarq.channels.SubscriberChannel;
import com.gruposantander.subscribersarq.dtos.CustodianInputDto;
import com.gruposantander.subscribersarq.services.SubscriberService;

@Component
public class SubscriberListener {

	@Autowired
	SubscriberService subscriberService;

	@StreamListener(SubscriberChannel.INPUT)
	public void subscribe(@Payload CustodianInputDto custodianInputDto) {
		this.subscriberService.saveCustodianLineage(custodianInputDto);
	}

}
