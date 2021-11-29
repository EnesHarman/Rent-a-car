package com.webproje.arackiralama.Business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webproje.arackiralama.Business.abstracts.CompanyManagerService;
import com.webproje.arackiralama.Business.abstracts.CompanyService;
import com.webproje.arackiralama.Business.constants.Messages;
import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Core.utilities.result.concretes.SuccessResult;
import com.webproje.arackiralama.Entity.concretes.City;
import com.webproje.arackiralama.Entity.concretes.Company;
import com.webproje.arackiralama.Entity.dto.companyDtos.CompanyAddDto;
import com.webproje.arackiralama.Entity.dto.vehicleDtos.VehicleDto;
import com.webproje.arackiralama.Repository.CompanyRepository;
@Service
public class CompanyManager implements CompanyService{
	private final CompanyRepository companyRepository;
	private final CompanyManagerService companyManagerService;

	@Autowired
	public CompanyManager(CompanyRepository companyRepository, CompanyManagerService companyManagerService) {
		super();
		this.companyRepository = companyRepository;
		this.companyManagerService = companyManagerService;
	}

	@Override
	public Result addCompany(CompanyAddDto companyAddDto) {
		Company company = new Company();
		company.setCompanyName(companyAddDto.getCompanyName());
		company.setAdress(companyAddDto.getAdress());
		company.setCity(new City(companyAddDto.getCityId()));
		company.setPhoneNumber(companyAddDto.getPhoneNumber());
		company.setPoint(0);
		company.setSiteUrl(companyAddDto.getSiteUrl());
		company.setVehicleNumber(0);
	
		Company insertedCompany = this.companyRepository.save(company);
		Result result = this.companyManagerService.addCompanyManager(companyAddDto.getCompanyManagerRegisterDto(), insertedCompany.getId());
		
		return new SuccessResult(Messages.companyAdded + "\n " + result.getMessage());
	}

}
