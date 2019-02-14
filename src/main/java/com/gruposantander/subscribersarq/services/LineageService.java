package com.gruposantander.subscribersarq.services;

import com.gruposantander.subscribersarq.dtos.CustodianInputDto;

public interface LineageService {
	
	void saveLineage(CustodianInputDto custodianInputDto);

}
