package com.gruposantander.subscribersarq.services;

import com.gruposantander.subscribersarq.dtos.CustodianInputDto;
import com.gruposantander.subscribersarq.dtos.OriginDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

	private CustodianInputDto toCustodianInputDto(GenericRecord genericRecord) {
		CustodianInputDto custodianInputDto = CustodianInputDto.builder().hash(this.convertToString(genericRecord.get("hash")))
				.uri(this.convertToString(genericRecord.get("uri"))).proc(this.convertToString(genericRecord.get("proc")))
				.version(this.convertToString(genericRecord.get("version"))).comment(this.convertToString(genericRecord.get("comment")))
				.origins(toOriginDtoList(genericRecord)).build();
		return custodianInputDto;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private List<OriginDto> toOriginDtoList(GenericRecord genericRecord) {
		List<OriginDto> originDtoList = new ArrayList<>();
		GenericData.Array genericDataArray = (GenericData.Array) genericRecord.get("origins");
		if (genericDataArray!=null && !genericDataArray.isEmpty()) {
			genericDataArray.forEach((r) -> originDtoList
					.add(OriginDto.builder().hash(this.convertToString(((GenericRecord) r).get("hash")))
							.uri(this.convertToString(((GenericRecord) r).get("uri"))).build()));

		}
		return originDtoList;
	}

	private String convertToString(Object object) {
		if (object != null) {
			return object.toString();
		}
		return null;
	}
}
