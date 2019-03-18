package com.gruposantander.subscribersarq.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Custodian {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUSTODIAN_SEQ")
    @SequenceGenerator(sequenceName = "custodian_seq", allocationSize = 1, name = "CUSTODIAN_SEQ")
	private Integer id;

	private String hash;

	private String uri;

	private String proc;

	private String version;

	@Column(insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	private String information;

}
