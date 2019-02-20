package com.gruposantander.subscribersarq.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gruposantander.subscribersarq.dtos.CustodianInputDto;
import com.gruposantander.subscribersarq.dtos.OriginDto;
import com.gruposantander.subscribersarq.models.Lineage;
import com.gruposantander.subscribersarq.repositories.LineageRepository;

@Service
public class LineageServiceImpl implements LineageService {

	@Autowired
	LineageRepository lineageRepository;

	@Override
	public Lineage save(Lineage lineage) {
		return this.lineageRepository.save(lineage);
	}

}
