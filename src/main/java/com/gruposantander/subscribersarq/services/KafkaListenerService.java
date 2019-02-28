package com.gruposantander.subscribersarq.services;

import java.util.Arrays;
import java.util.List;

import org.apache.avro.generic.GenericRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.gruposantander.subscribersarq.dtos.CustodianInputDto;
import com.gruposantander.subscribersarq.dtos.OriginDto;
import com.gruposantander.subscribersarq.models.Custodian;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaListenerService {

	@Autowired
	SubscriberService subscriberService;
	
	@KafkaListener(topics = "${spring.kafka.topic}")
    public void subscribe(GenericRecord genericRecord) {
		log.info(genericRecord.toString());
		this.subscriberService.saveCustodianLineages(this.toCustodianInputDto(genericRecord));
    }
	
	private CustodianInputDto toCustodianInputDto (GenericRecord genericRecord) {
		CustodianInputDto custodianInputDto = CustodianInputDto.builder()
				.hash(genericRecord.get("hash").toString())
				.uri(genericRecord.get("uri").toString())
				.proc(genericRecord.get("proc").toString())
				.version(genericRecord.get("version").toString())
				.comment(genericRecord.get("comment").toString())
				.origins(null).build();
		return custodianInputDto;
	}
}
