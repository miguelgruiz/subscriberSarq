package com.gruposantander.subscribersarq.services;

import com.gruposantander.subscribersarq.dtos.CustodianInputDto;

public interface CustodianService {

	void saveCustodian(CustodianInputDto custodianInputDto);

	boolean existCustodian(String hash, String uri);
}
