package com.webproje.arackiralama.Entity.dto.companyDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyListForManagerRegisterDto {
	private int companyId;
	private String companyName;

}
