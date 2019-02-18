package com.gruposantander.subscribersarq.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gruposantander.subscribersarq.dtos.CustodianInputDto;
import com.gruposantander.subscribersarq.models.Custodian;
import com.gruposantander.subscribersarq.repositories.CustodianRepository;

@Service
public class CustodianServiceImpl implements CustodianService {

	@Autowired 
	CustodianRepository custodianRepository; 
	
	@Override
	public void saveCustodian(CustodianInputDto custodianInputDto) {
		Custodian custodian = Custodian.builder().hash(custodianInputDto.getHash()).uri(custodianInputDto.getUri())
		.proc(custodianInputDto.getProc()).version(custodianInputDto.getVersion())
		.comment(custodianInputDto.getComment()).build();
		this.custodianRepository.save(custodian);
	}
	
}
