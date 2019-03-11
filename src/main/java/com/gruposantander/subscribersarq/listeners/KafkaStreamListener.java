package com.gruposantander.subscribersarq.listeners;

import java.util.ArrayList;
import java.util.List;

import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import com.gruposantander.subscribersarq.channels.KafkaStreamChannel;
import com.gruposantander.subscribersarq.dtos.CustodianInputDto;
import com.gruposantander.subscribersarq.dtos.OriginDto;
import com.gruposantander.subscribersarq.services.CustodianLineages;
import com.gruposantander.subscribersarq.services.SubscriberService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaStreamListener {

	@Autowired
	SubscriberService subscriberService;

	@StreamListener(KafkaStreamChannel.INPUT)
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
