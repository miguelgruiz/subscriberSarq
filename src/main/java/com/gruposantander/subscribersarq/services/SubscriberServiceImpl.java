package com.gruposantander.subscribersarq.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gruposantander.subscribersarq.dtos.CustodianInputDto;
import com.gruposantander.subscribersarq.dtos.OriginDto;
import com.gruposantander.subscribersarq.models.Custodian;
import com.gruposantander.subscribersarq.models.Lineage;

@Service
public class SubscriberServiceImpl implements SubscriberService {

	@Autowired
	CustodianService custodianService;
	
	@Autowired
	LineageService lineageService;
	
	@Override
	public CustodianLineages saveCustodianLineages(CustodianInputDto custodianInputDto) {
		Custodian custodian = saveCustodian(custodianInputDto);
		List<Lineage> lineageList = saveLineages(custodianInputDto);
		return CustodianLineages.builder().custodian(custodian).lineagesList(lineageList).build();
	}
	
	private Custodian saveCustodian(CustodianInputDto custodianInputDto) {
		Custodian custodian = Custodian.builder().hash(custodianInputDto.getHash()).uri(custodianInputDto.getUri())
				.proc(custodianInputDto.getProc()).version(custodianInputDto.getVersion()).comment(custodianInputDto.getComment()).build();
		return this.custodianService.save(custodian);
	}
	
	private List<Lineage> saveLineages(CustodianInputDto custodianInputDto) {
		List<Lineage> lineageList = new ArrayList<Lineage>();
		List<OriginDto> originDtoList = custodianInputDto.getOrigins();
		for (OriginDto originDto: originDtoList) {
			Lineage lineage = Lineage.builder().hash(custodianInputDto.getHash()).hashOrigin(originDto.getHash()).uri(custodianInputDto.getUri())
					.uriOrigin(originDto.getUri()).build();
			lineageList.add(this.lineageService.save(lineage));
		}
		return lineageList;
	}
	
}
