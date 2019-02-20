package com.gruposantander.subscribersarq.services;

import com.gruposantander.subscribersarq.dtos.CustodianInputDto;
import com.gruposantander.subscribersarq.dtos.OriginDto;
import com.gruposantander.subscribersarq.models.Custodian;
import com.gruposantander.subscribersarq.repositories.CustodianRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustodianServiceIT {

	@Autowired
	private CustodianService custodianService;

	@Autowired
	private CustodianRepository custodianRepository;

	@Test
	public void testSave() {
		OriginDto originDto = OriginDto.builder().hash("0000001").uri("http://ejemplo1.es").build();
		CustodianInputDto custodianInputDto = CustodianInputDto.builder().hash("0000002").uri("http://ejemplo2.es").proc("P2")
				.version("v2.3.l8").comment("Esto es un comentario").originDtoList(Arrays.asList(originDto)).build();
		Custodian custodian = this.custodianService.save(custodianInputDto);
		assertNotNull(custodian);
		this.custodianRepository.deleteById(custodian.getId());
	}
}
