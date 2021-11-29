package com.webproje.arackiralama.Business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webproje.arackiralama.Business.abstracts.AppUserService;
import com.webproje.arackiralama.Business.abstracts.SystemManagerService;
import com.webproje.arackiralama.Business.constants.Messages;
import com.webproje.arackiralama.Core.entity.concretes.AppUser;
import com.webproje.arackiralama.Core.entity.concretes.Role;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Core.utilities.result.concretes.SuccessResult;
import com.webproje.arackiralama.Entity.concretes.SystemManager;
import com.webproje.arackiralama.Entity.dto.systemManagerDtos.SystemManagerRegisterDto;
import com.webproje.arackiralama.Repository.SystemManagerRepository;

@Service
public class SystemManagerManager implements SystemManagerService {

	private final SystemManagerRepository managerRepository;
	private final AppUserService appUserService;
	
	@Autowired
	public SystemManagerManager(SystemManagerRepository managerRepository, AppUserService appUserService) {
		super();
		this.managerRepository = managerRepository;
		this.appUserService = appUserService;
	}

	@Override
	public Result register(SystemManagerRegisterDto managerRegisterDto) {
		AppUser user = new AppUser();
		user.setEmail(managerRegisterDto.getEmail());
		user.setPassword(managerRegisterDto.getPassword());
		user.setRole(new Role(4,"ROLE_SYSTEM_MANAGER"));
		AppUser insertedUser = this.appUserService.saveUser(user);
		
		SystemManager manager = new SystemManager();
		manager.setName(managerRegisterDto.getName());
		manager.setSurname(managerRegisterDto.getSurname());
		manager.setUser(insertedUser);
		this.managerRepository.save(manager);
		
		return new SuccessResult(Messages.systemManagerRegistered);
	}

}
