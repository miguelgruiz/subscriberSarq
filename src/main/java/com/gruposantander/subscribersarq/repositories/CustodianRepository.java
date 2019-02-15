package com.gruposantander.subscribersarq.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gruposantander.subscribersarq.models.Custodian;

public interface CustodianRepository extends JpaRepository<Custodian, Integer> {

	Custodian findByHashAndUri(String hash, String uri);
}
