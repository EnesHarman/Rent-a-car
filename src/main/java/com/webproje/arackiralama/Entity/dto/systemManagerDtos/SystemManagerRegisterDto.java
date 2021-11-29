package com.webproje.arackiralama.Entity.dto.systemManagerDtos;

import com.webproje.arackiralama.Entity.dto.appUserDtos.AppUserRegisterDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemManagerRegisterDto  extends AppUserRegisterDto{
	
	private String name;
	private String surname;
}
