package com.gruposantander.subscribersarq.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustodianInputDto extends CustodianOutputDto {

	private List<OriginDto> origins;
	
	@Override
    public String toString() {
        return "[hash=" + getHash() + ", uri=" + getUri() + ", proc=" + getProc() + ", version=" + getVersion() + ", comment=" + getComment() + ", origins=" + origins + "]";
    }
	
}
