package com.webproje.arackiralama.Entity.dto.companyManagerDtos;

import com.webproje.arackiralama.Entity.dto.appUserDtos.AppUserRegisterDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyManagerRegisterDto extends AppUserRegisterDto{
	private int companyId;
}
