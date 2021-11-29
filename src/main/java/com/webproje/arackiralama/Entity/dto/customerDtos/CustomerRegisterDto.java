package com.webproje.arackiralama.Entity.dto.customerDtos;

import com.webproje.arackiralama.Entity.dto.appUserDtos.AppUserRegisterDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRegisterDto extends AppUserRegisterDto{
	
	private String name;
	
	private String surname;

	private String identityNumber;
	
	private String phoneNumber;
	
}
