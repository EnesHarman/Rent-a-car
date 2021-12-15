package com.webproje.arackiralama.Business.concretes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webproje.arackiralama.Business.abstracts.CompanyManagerService;
import com.webproje.arackiralama.Business.abstracts.CompanyService;
import com.webproje.arackiralama.Business.constants.Messages;
import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Core.utilities.result.concretes.SuccessDataResult;
import com.webproje.arackiralama.Core.utilities.result.concretes.SuccessResult;
import com.webproje.arackiralama.Entity.concretes.City;
import com.webproje.arackiralama.Entity.concretes.Company;
import com.webproje.arackiralama.Entity.dto.companyDtos.CompanyAddDto;
import com.webproje.arackiralama.Entity.dto.companyDtos.CompanyListDto;
import com.webproje.arackiralama.Repository.CompanyRepository;

import lombok.extern.slf4j.Slf4j;
@Slf4j
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
		
		log.info("A company added to the service with "+ company.getCompanyName()+ " name.");
		return new SuccessResult(Messages.companyAdded + "\n " + result.getMessage());
	}

	@Override
	public Result deleteCompany(int companyId) {
		this.companyRepository.deleteById(companyId);
		log.info("A company with "+companyId+" id has been deleted.");
		return new SuccessResult(Messages.companyDeleted);
	}

	@Override
	public DataResult<List<CompanyListDto>> listCompany(Optional<Integer> companyId) {
		List<CompanyListDto> companies = new ArrayList<CompanyListDto>();
		if(companyId.isPresent()) {
			companies.add(this.companyRepository.listSingCompany(companyId.get()));
		}
		else {
			companies = this.companyRepository.listAllCompanies();
		}
		return new SuccessDataResult<List<CompanyListDto>>(companies);
	}


}
