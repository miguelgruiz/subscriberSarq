package com.gruposantander.subscribersarq.mappers;

import org.springframework.stereotype.Service;

import com.gruposantander.subscribersarq.dtos.CustodianInputDto;
import com.gruposantander.subscribersarq.models.Custodian;

@Service
public class MapperCustodianInputDtoToCustodian {
	
	public Custodian mapper(CustodianInputDto custodianInputDto) {
		
		return Custodian.builder().hash(custodianInputDto.getHash()).uri(custodianInputDto.getUri())
				.proc(custodianInputDto.getProc()).version(custodianInputDto.getVersion())
				.comment(custodianInputDto.getComment()).build();
		
	}

}
