package com.gruposantander.subscribersarq.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gruposantander.subscribersarq.dtos.CustodianInputDto;

@Service
public class SubscriberServiceImpl implements SubscriberService {

	@Autowired
	CustodianService custodianService;
	
	@Autowired
	LineageService LineageService;
	
	@Override
	public void saveCustodianLineage(CustodianInputDto custodianInputDto) {
		this.custodianService.saveCustodian(custodianInputDto);
		this.LineageService.saveLineage(custodianInputDto);
	}
	
}
