package com.gruposantander.subscribersarq.services;

import com.gruposantander.subscribersarq.dtos.CustodianInputDto;
import com.gruposantander.subscribersarq.models.Lineage;

public interface LineageService {
	
	Lineage save(Lineage lineage);

}
