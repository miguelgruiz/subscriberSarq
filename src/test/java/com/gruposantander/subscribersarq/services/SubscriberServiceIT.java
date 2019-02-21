package com.gruposantander.subscribersarq.services;

import com.gruposantander.subscribersarq.dtos.CustodianInputDto;
import com.gruposantander.subscribersarq.dtos.OriginDto;
import com.gruposantander.subscribersarq.models.Lineage;
import com.gruposantander.subscribersarq.repositories.CustodianRepository;
import com.gruposantander.subscribersarq.repositories.LineageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SubscriberServiceIT {

	@Autowired
	private CustodianRepository custodianRepository;

	@Autowired
	private LineageRepository lineageRepository;

	@Autowired
	private SubscriberService subscriberService;

	@Test
	public void testSaveCustodianLineage() {
		OriginDto originDto1 = OriginDto.builder().hash("0000001").uri("http://ejemplo1.es").build();
		OriginDto originDto2 = OriginDto.builder().hash("0000002").uri("http://ejemplo2.es").build();
		CustodianInputDto custodianInputDto = CustodianInputDto.builder().hash("0000003").uri("http://ejemplo3.es").proc("P3")
				.version("v3.11.0").comment("Esto es un comentario").origins(Arrays.asList(originDto1, originDto2)).build();
		CustodianLineages custodianLineages = this.subscriberService.saveCustodianLineages(custodianInputDto);
		assertNotNull(custodianLineages);
		assertEquals(custodianInputDto.getHash(), custodianLineages.getCustodian().getHash());
		assertTrue(custodianLineages.getLineagesList().size() >= 2);
		this.custodianRepository.deleteById(custodianLineages.getCustodian().getId());
		for (Lineage lineage: custodianLineages.getLineagesList()) {
			this.lineageRepository.deleteById(lineage.getId());
		}
	}
}
