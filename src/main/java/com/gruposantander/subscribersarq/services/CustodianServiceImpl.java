package com.gruposantander.subscribersarq.services;

import com.gruposantander.subscribersarq.dtos.CustodianInputDto;
import com.gruposantander.subscribersarq.models.Custodian;
import com.gruposantander.subscribersarq.repositories.CustodianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustodianServiceImpl implements CustodianService {

	@Autowired
	CustodianRepository custodianRepository;

	@Override
	public Custodian save(CustodianInputDto custodianInputDto) {
		Custodian custodian = Custodian.builder().hash(custodianInputDto.getHash()).uri(custodianInputDto.getUri())
				.proc(custodianInputDto.getProc()).version(custodianInputDto.getVersion()).comment(custodianInputDto.getComment()).build();
		return this.custodianRepository.save(custodian);
	}

}
