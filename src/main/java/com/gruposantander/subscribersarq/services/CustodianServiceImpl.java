package com.gruposantander.subscribersarq.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gruposantander.subscribersarq.models.Custodian;
import com.gruposantander.subscribersarq.repositories.CustodianRepository;

@Service
public class CustodianServiceImpl implements CustodianService {

	@Autowired
	CustodianRepository custodianRepository;

	@Override
	public Custodian save(Custodian custodian) {
		return this.custodianRepository.save(custodian);
	}

}
