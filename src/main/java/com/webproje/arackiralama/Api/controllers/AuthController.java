package com.webproje.arackiralama.Api.controllers;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.webproje.arackiralama.Business.abstracts.AuthService;
import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.concretes.ErrorResult;
import com.webproje.arackiralama.Entity.dto.auth.LoginDto;
import com.webproje.arackiralama.Entity.dto.auth.LoginSuccessDto;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private AuthService authService;

	@Autowired
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDto loginDto ){
		DataResult<LoginSuccessDto> result = this.authService.login(loginDto);
		if(result.getSuccess()) {
			return ResponseEntity.ok(result.getData());
		}
		else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorResult handleDuplicateError(BadCredentialsException exceptions){
		return new ErrorResult("Incorrect username or password. Please check your inputs.");
	}
	
	
	
}

