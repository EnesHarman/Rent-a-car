package com.webproje.arackiralama.Entity.dto.appUserDtos;

import lombok.Data;

@Data
public abstract class AppUserRegisterDto {
	
	private String email;
	
	private String password;
}
