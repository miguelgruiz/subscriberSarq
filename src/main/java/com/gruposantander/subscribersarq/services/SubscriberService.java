package com.gruposantander.subscribersarq.services;

import com.gruposantander.subscribersarq.dtos.CustodianInputDto;

public interface SubscriberService {

	void saveLineagesCustodian(CustodianInputDto custodianInputDto);
}
