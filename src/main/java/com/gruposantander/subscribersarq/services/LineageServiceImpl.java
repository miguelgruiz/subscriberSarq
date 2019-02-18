package com.gruposantander.subscribersarq.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gruposantander.subscribersarq.dtos.CustodianInputDto;
import com.gruposantander.subscribersarq.dtos.OriginDto;
import com.gruposantander.subscribersarq.models.Lineage;
import com.gruposantander.subscribersarq.repositories.LineageRepository;

@Service
public class LineageServiceImpl implements LineageService {
	
	@Autowired
	LineageRepository lineageRepository;
	
	@Override
	public void saveLineage(CustodianInputDto custodianInputDto) {
		List<Lineage> listLineage =  new ArrayList<Lineage>();
		List<OriginDto> listOriginDto = custodianInputDto.getOrigins();
		for(OriginDto originDto : listOriginDto){
			listLineage.add(Lineage.builder().hash(custodianInputDto.getHash()).uri(custodianInputDto.getUri()).hashOrigin(originDto.getHash()).uriOrigin(originDto.getUri()).build());
		}
		this.lineageRepository.saveAll(listLineage);
	}

}
