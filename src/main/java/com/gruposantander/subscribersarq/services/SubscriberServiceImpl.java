package com.gruposantander.subscribersarq.services;

import com.gruposantander.subscribersarq.dtos.OriginDto;
import com.gruposantander.subscribersarq.models.Custodian;
import com.gruposantander.subscribersarq.models.Lineage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gruposantander.subscribersarq.dtos.CustodianInputDto;

import java.util.List;

@Service
public class SubscriberServiceImpl implements SubscriberService {

	@Autowired
	CustodianService custodianService;
	
	@Autowired
	LineageService lineageService;
	
	@Override
	public void saveLineagesCustodian(CustodianInputDto custodianInputDto) {
		this.custodianService.save(custodianInputDto);
		List<OriginDto> originDtoList = custodianInputDto.getOriginDtoList();
		for (OriginDto originDto: originDtoList) {
			Lineage lineage = Lineage.builder().hash(custodianInputDto.getHash()).hashOrigin(originDto.getHash()).uri(custodianInputDto.getUri())
					.uriOrigin(originDto.getUri()).build();
			this.lineageService.save(lineage);
		}
	}
	
}
