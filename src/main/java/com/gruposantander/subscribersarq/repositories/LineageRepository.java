package com.gruposantander.subscribersarq.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gruposantander.subscribersarq.models.Lineage;

public interface LineageRepository extends JpaRepository<Lineage, Integer> {

}
