package com.gruposantander.subscribersarq.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gruposantander.subscribersarq.dtos.CustodianInputDto;
import com.gruposantander.subscribersarq.dtos.OriginDto;
import com.gruposantander.subscribersarq.models.Lineage;

@Service
public class MapperCustodianInputDtoToLineage {

	public List<Lineage> mapper(CustodianInputDto custodianInputDto) {
		
		List<Lineage> listLineage =  new ArrayList<Lineage>();
		List<OriginDto> listOriginDto = custodianInputDto.getOrigins();
		for(OriginDto originDto : listOriginDto){
			listLineage.add(Lineage.builder().hash(custodianInputDto.getHash()).uri(custodianInputDto.getUri()).hashOrigin(originDto.getHash()).uriOrigin(originDto.getUri()).build());
		}
		
		return listLineage;
	}

}
