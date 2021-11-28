package com.webproje.arackiralama.Entity.dto;

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
