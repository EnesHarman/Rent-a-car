package com.webproje.arackiralama.Entity.dto.companyDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyListDto {
	
	private int id;

	private String companyName;

	private String adress;

	private float point = 0f;

	private String siteUrl;

	private String phoneNumber;
	
	private String city;

	private String managerEmail;
}
