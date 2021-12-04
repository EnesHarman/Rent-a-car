package com.webproje.arackiralama.Api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webproje.arackiralama.Business.abstracts.RoleService;
import com.webproje.arackiralama.Core.entity.concretes.Role;
import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;

@RestController
@RequestMapping("/api/role")
public class RoleController {

	private final RoleService roleService;

	public RoleController(RoleService roleService) {
		super();
		this.roleService = roleService;
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addRole(@RequestBody Role role){
		Result result = this.roleService.addRole(role);
		if(result.getSuccess()) {
			return ResponseEntity.ok(result.getMessage());
		}
		else {
			return ResponseEntity.badRequest().body(result.getMessage());
		}
	}
	
	@DeleteMapping("/delete/{roleId}")
	public ResponseEntity<?> deleteRole(@PathVariable int roleId){
		Result result = this.roleService.deleteRole(roleId);
		if(result.getSuccess()) {
			return ResponseEntity.ok(result.getMessage());
		}
		else {
			return ResponseEntity.badRequest().body(result.getMessage());
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateRole(@RequestBody Role role){
		Result result = this.roleService.updateRole(role);
		if(result.getSuccess()) {
			return ResponseEntity.ok(result.getMessage());
		}
		else {
			return ResponseEntity.badRequest().body(result.getMessage());
		}
	}
	
	@GetMapping("/list")
	public ResponseEntity<?> updateRole(){
		DataResult<List<Role>> result = this.roleService.listRoles();
		if(result.getSuccess()) {
			return ResponseEntity.ok(result.getData());
		}
		else {
			return ResponseEntity.badRequest().body(result.getMessage());
		}
	}
}
