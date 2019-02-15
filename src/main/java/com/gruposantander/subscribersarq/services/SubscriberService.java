package com.gruposantander.subscribersarq.services;

import com.gruposantander.subscribersarq.dtos.CustodianInputDto;

public interface SubscriberService {

	void saveCustodianLineage(CustodianInputDto custodianInputDto);
}
