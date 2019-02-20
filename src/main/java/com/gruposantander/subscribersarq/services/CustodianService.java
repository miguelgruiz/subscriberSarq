package com.gruposantander.subscribersarq.services;

import com.gruposantander.subscribersarq.dtos.CustodianInputDto;
import com.gruposantander.subscribersarq.models.Custodian;

public interface CustodianService {

	Custodian save(CustodianInputDto custodianInputDto);

}
