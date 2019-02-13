package com.gruposantander.subscribersarq.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.gruposantander.subscribersarq.dtos.CustodianInputDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustodianSubscriberService {
	
	@KafkaListener(topics = "${spring.kafka.topic}", groupId = "group_id")
    public void subscribe(CustodianInputDto custodianInputDto) {
        log.info(custodianInputDto.toString());
    }

}
