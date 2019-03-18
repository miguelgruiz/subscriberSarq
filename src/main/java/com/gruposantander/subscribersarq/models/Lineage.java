package com.gruposantander.subscribersarq.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Lineage {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LINEAGE_SEQ")
    @SequenceGenerator(sequenceName = "lineage_seq", allocationSize = 1, name = "LINEAGE_SEQ")
	private Integer id;

	private String hash;

	private String uri;

	private String hashOrigin;

	private String uriOrigin;
}
