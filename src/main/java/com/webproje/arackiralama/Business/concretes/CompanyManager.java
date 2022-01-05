package com.webproje.arackiralama.Business.concretes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.webproje.arackiralama.Business.abstracts.CompanyService;
import com.webproje.arackiralama.Business.constants.Messages;
import com.webproje.arackiralama.Core.utilities.emailSender.EmailSenderService;
import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Core.utilities.result.concretes.ErrorDataResult;
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
	private final EmailSenderService emailSenderService;

	@Autowired
	public CompanyManager(CompanyRepository companyRepository,EmailSenderService emailSenderService) {
		this.companyRepository = companyRepository;
		this.emailSenderService = emailSenderService;
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
		company.setManagerEmail(companyAddDto.getManagerEmail());
		Company insertedCompany = this.companyRepository.save(company);
	
		log.info("A company added to the service with "+ company.getCompanyName()+ " name.");
		this.emailSenderService.sendEmail(companyAddDto.getManagerEmail(),Messages.companyAddedServiceEmailBody + insertedCompany.getId(), Messages.companyAddedServiceEmailSubject);
		return new SuccessResult(Messages.companyAdded);
	}

	@Override
	public Result deleteCompany(int companyId) {
		this.companyRepository.deleteById(companyId);
		log.info("A company with "+companyId+" id has been deleted.");
		return new SuccessResult(Messages.companyDeleted);
	}

	@Override
	public DataResult<List<CompanyListDto>> listCompany(Optional<Integer> companyId,Optional<Integer> pageSize, Optional<Integer> pageNum) {
		int _pageSize = pageSize.isPresent() && pageSize.get()<20 && pageSize.get()>10 ?pageSize.get() : 10;
		int _pageNum = pageNum.isPresent() && pageNum.get()>0 ? pageNum.get(): 1;
		Pageable pageable = PageRequest.of(_pageNum-1, _pageSize);
		
		List<CompanyListDto> companies = new ArrayList<CompanyListDto>();
		if(companyId.isPresent()) {
			companies.add(this.companyRepository.listSingCompany(companyId.get()));
		}
		else {
			companies = this.companyRepository.listAllCompanies(pageable);
		}
		return new SuccessDataResult<List<CompanyListDto>>(companies);
	}

	@Override
	public DataResult<Company> getById(int companyId) {
		Company company = this.companyRepository.findById(companyId).orElse(null);
		if(company !=null) {
			return new SuccessDataResult<Company>(company);
		}
		return new ErrorDataResult<Company>();
	}

	@Override
	public Result save(Company company) {
		this.companyRepository.save(company);
		return new SuccessResult();
	}

}
