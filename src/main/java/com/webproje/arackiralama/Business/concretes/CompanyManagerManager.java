package com.webproje.arackiralama.Business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webproje.arackiralama.Business.abstracts.AppUserService;
import com.webproje.arackiralama.Business.abstracts.CompanyManagerService;
import com.webproje.arackiralama.Business.constants.Messages;
import com.webproje.arackiralama.Core.entity.concretes.AppUser;
import com.webproje.arackiralama.Core.entity.concretes.Role;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Core.utilities.result.concretes.SuccessResult;
import com.webproje.arackiralama.Entity.concretes.Company;
import com.webproje.arackiralama.Entity.concretes.CompanyManager;
import com.webproje.arackiralama.Entity.dto.CompanyManagerRegisterDto;
import com.webproje.arackiralama.Repository.AppUserRepository;
import com.webproje.arackiralama.Repository.CompanyManagerRepository;

@Service
public class CompanyManagerManager implements CompanyManagerService{

	private final CompanyManagerRepository companyManagerRepository;
	private final AppUserService appUserService;
	
	@Autowired
	public CompanyManagerManager(CompanyManagerRepository companyManagerRepository,AppUserService appUserService) {
		super();
		this.companyManagerRepository = companyManagerRepository;
		this.appUserService = appUserService;
	}

	@Override
	public Result addCompanyManager(CompanyManagerRegisterDto companyManagerRegisterDto, int companyId) {
		AppUser user = new AppUser();
		user.setEmail(companyManagerRegisterDto.getEmail());
		user.setPassword(companyManagerRegisterDto.getPassword());
		user.setRole(new Role(3,"ROLE_COMPANY_MANAGER"));
		AppUser insertedUser = this.appUserService.saveUser(user);
		
		CompanyManager companyManager = new CompanyManager();
		companyManager.setCompany(new Company(companyId));
		companyManager.setUser(insertedUser);
		this.companyManagerRepository.save(companyManager);
		return new SuccessResult(Messages.companyManagerAdded);
	}

}
