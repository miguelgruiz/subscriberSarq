package com.gruposantander.subscribersarq.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gruposantander.subscribersarq.models.Custodian;
import com.gruposantander.subscribersarq.repositories.CustodianRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustodianServiceIT {

	@Autowired
	private CustodianService custodianService;

	@Autowired
	private CustodianRepository custodianRepository;

	@Test
	public void testSave() {
		
		Custodian custodianMock = Custodian.builder().hash("0000002").uri("http://ejemplo2.es").proc("P2")
				.version("v2.3.l8").comment("Esto es un comentario").build();
		Custodian custodian = this.custodianService.save(custodianMock);
		assertNotNull(custodian);
		assertEquals(custodianMock.getHash(), custodian.getHash());
		assertEquals(custodianMock.getUri(), custodian.getUri());
		assertEquals(custodianMock.getProc(), custodian.getProc());
		assertEquals(custodianMock.getVersion(), custodian.getVersion());
		assertEquals(custodianMock.getComment(), custodian.getComment());
		this.custodianRepository.deleteById(custodian.getId());
	}
}
