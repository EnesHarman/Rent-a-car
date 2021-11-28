package com.webproje.arackiralama.Entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyAddDto {

	private String companyName;
	
	private String adress;

	private String siteUrl;
	
	private String phoneNumber;
	
	private int cityId;
	
	private CompanyManagerRegisterDto companyManagerRegisterDto;
}
