package com.gruposantander.subscribersarq.services;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gruposantander.subscribersarq.models.Custodian;
import com.gruposantander.subscribersarq.models.Lineage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustodianLineages {

	Custodian custodian;
	
	List<Lineage> lineagesList;
}
