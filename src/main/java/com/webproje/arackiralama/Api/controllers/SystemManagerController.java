package com.webproje.arackiralama.Api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webproje.arackiralama.Business.abstracts.SystemManagerService;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Entity.dto.systemManagerDtos.SystemManagerRegisterDto;

@RestController
@RequestMapping("/api/systemmanager")
public class SystemManagerController {
	
	private final SystemManagerService systemManagerService;

	@Autowired
	public SystemManagerController(SystemManagerService systemManagerService) {
		super();
		this.systemManagerService = systemManagerService;
	}
	//TODO Denemeler bittikten sonra kaldırılacak
	@PostMapping("/register")
	public ResponseEntity<?> registerSystemManager(@RequestBody SystemManagerRegisterDto managerRegisterDto){
		Result result = this.systemManagerService.register(managerRegisterDto);
		if(result.getSuccess()) {
			return ResponseEntity.ok(result.getMessage());
		}
		else {
			return ResponseEntity.badRequest().body(result.getMessage());
		}
	}
	
}
