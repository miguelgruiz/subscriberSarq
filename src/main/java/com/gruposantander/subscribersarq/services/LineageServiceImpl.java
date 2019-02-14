package com.gruposantander.subscribersarq.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gruposantander.subscribersarq.dtos.CustodianInputDto;
import com.gruposantander.subscribersarq.mappers.MapperCustodianInputDtoToLineage;
import com.gruposantander.subscribersarq.repositories.LineageRepository;

@Service
public class LineageServiceImpl implements LineageService {
	
	@Autowired
	LineageRepository lineageRepository;
	
	@Autowired
	MapperCustodianInputDtoToLineage mapperCustodianInputDtoToLineage;
	
	@Override
	public void saveLineage(CustodianInputDto custodianInputDto) {
		this.lineageRepository.saveAll(mapperCustodianInputDtoToLineage.mapper(custodianInputDto));
	}

}
