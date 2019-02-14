package com.gruposantander.subscribersarq.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(Custodian.IdClass.class)
public class Custodian {

	@Id
	private String hash;

	@Id
	private String uri;

	private String proc;

	private String version;

	@Column(insertable = false, updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	private String comment;

	@Data
	static class IdClass implements Serializable {
		private String hash;
		private String uri;
	}
}
