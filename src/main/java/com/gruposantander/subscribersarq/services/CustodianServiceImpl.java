package com.gruposantander.subscribersarq.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gruposantander.subscribersarq.dtos.CustodianInputDto;
import com.gruposantander.subscribersarq.mappers.MapperCustodianInputDtoToCustodian;
import com.gruposantander.subscribersarq.repositories.CustodianRepository;

@Service
public class CustodianServiceImpl implements CustodianService {

	@Autowired 
	CustodianRepository custodianRepository; 
	
	@Autowired
	MapperCustodianInputDtoToCustodian mapperCustodianInputDtoToCustodian;
	
	@Override
	public void saveCustodian(CustodianInputDto custodianInputDto) {
		this.custodianRepository.save(mapperCustodianInputDtoToCustodian.mapper(custodianInputDto));
	}
}
