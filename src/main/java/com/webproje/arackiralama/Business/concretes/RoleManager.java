package com.webproje.arackiralama.Business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webproje.arackiralama.Business.abstracts.RoleService;
import com.webproje.arackiralama.Business.constants.Messages;
import com.webproje.arackiralama.Core.entity.concretes.Role;
import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Core.utilities.result.concretes.SuccessDataResult;
import com.webproje.arackiralama.Core.utilities.result.concretes.SuccessResult;
import com.webproje.arackiralama.Repository.RoleRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoleManager implements RoleService{

	private final RoleRepository roleRepository;
	
	@Autowired
	public RoleManager(RoleRepository roleRepository) {
		super();
		this.roleRepository = roleRepository;
	}

	@Override
	public Result addRole(Role role) {
		this.roleRepository.save(role);
		log.info("A role has added to the service with "+ role.getName()+ " name.");
		return new SuccessResult(Messages.roleAdded);
	}

	@Override
	public Result deleteRole(int roleId) {
		this.roleRepository.deleteById(roleId);
		log.info("A role has deleted from the service with "+ roleId+ " id.");
		return new SuccessResult(Messages.roleDeleted);
	}

	@Override
	public Result updateRole(Role role) {
		Role _role = this.roleRepository.getById(role.getId());
		log.info("A role's name has been updated to"+role.getName()+" from "+ _role.getName());
		_role.setName(role.getName());
		this.roleRepository.save(_role);
		return new SuccessResult(Messages.roleUpdated);
	}

	@Override
	public DataResult<List<Role>> listRoles() {
		
		return new SuccessDataResult<List<Role>>(this.roleRepository.findAll());
	}
	
}
