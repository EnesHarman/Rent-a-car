package com.webproje.arackiralama.Business.concretes;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.webproje.arackiralama.Business.abstracts.AppUserService;
import com.webproje.arackiralama.Business.abstracts.CarRentalService;
import com.webproje.arackiralama.Business.abstracts.CompanyManagerService;
import com.webproje.arackiralama.Business.abstracts.CompanyService;
import com.webproje.arackiralama.Business.constants.CompanyPoints;
import com.webproje.arackiralama.Business.constants.Messages;
import com.webproje.arackiralama.Core.entity.concretes.AppUser;
import com.webproje.arackiralama.Core.entity.concretes.Role;
import com.webproje.arackiralama.Core.utilities.emailSender.EmailSenderService;
import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Core.utilities.result.concretes.ErrorDataResult;
import com.webproje.arackiralama.Core.utilities.result.concretes.ErrorResult;
import com.webproje.arackiralama.Core.utilities.result.concretes.SuccessDataResult;
import com.webproje.arackiralama.Core.utilities.result.concretes.SuccessResult;
import com.webproje.arackiralama.Entity.concretes.Company;
import com.webproje.arackiralama.Entity.concretes.CompanyManager;
import com.webproje.arackiralama.Entity.dto.carRentalsDtos.CarRentalListDto;
import com.webproje.arackiralama.Entity.dto.companyManagerDtos.CompanyManagerRegisterDto;
import com.webproje.arackiralama.Repository.CompanyManagerRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompanyManagerManager implements CompanyManagerService{

	private final CompanyManagerRepository companyManagerRepository;
	private final AppUserService appUserService;
	private final CarRentalService carRentalService;
	private final EmailSenderService emailSenderService;
	private final CompanyService companyService;

	
	@Autowired
	public CompanyManagerManager(CompanyManagerRepository companyManagerRepository,AppUserService appUserService, CarRentalService carRentalService,EmailSenderService emailSenderService,CompanyService companyService) {
		super();
		this.companyManagerRepository = companyManagerRepository;
		this.appUserService = appUserService;
		this.carRentalService = carRentalService;
		this.emailSenderService = emailSenderService;
		this.companyService = companyService;
	}
	
	
	@Override
	public Result register(CompanyManagerRegisterDto companyManagerRegisterDto) {
		DataResult<Company> company = this.companyService.getById(companyManagerRegisterDto.getCompanyId());
		if(!company.getSuccess()) {
			return new ErrorResult(Messages.companyNotExist);
		}
		 if(company.getData().getManager()!=null) {
			return new ErrorResult(Messages.companyHasManager);
		}

		 if(!company.getData().getManagerEmail().equals(companyManagerRegisterDto.getEmail())) {
			return new ErrorResult(Messages.companyManagerEmailError);
		}
	
		AppUser user = new AppUser();
		user.setEmail(companyManagerRegisterDto.getEmail());
		user.setPassword(companyManagerRegisterDto.getPassword());
		user.setRole(new Role(3,"ROLE_COMPANY_MANAGER"));
		AppUser insertedUser = this.appUserService.saveUser(user);
			
		CompanyManager companyManager = new CompanyManager();
		companyManager.setCompany(company.getData());
		companyManager.setAppUser(insertedUser);
		this.companyManagerRepository.save(companyManager);
			
		log.info("A company manager has added to the service with this information:\nEmail : "+companyManagerRegisterDto.getEmail());
			
		return new SuccessResult(Messages.companyManagerAdded);
		
		
	}
	

	@Override
	public DataResult<Integer> getCompanyIdByManagerEmail(String email) {
		DataResult<AppUser>user = this.appUserService.getUserByEmail(email);
		CompanyManager companyManager = this.companyManagerRepository.getByAppUser_UserId(user.getData().getUserId());
		return new SuccessDataResult<Integer>(companyManager.getCompany().getId());
	}

	@Override
	public DataResult<List<CarRentalListDto>> listCarRentalRequests(Optional<Integer> pageSize,
			Optional<Integer> pageNum) {
		int _pageSize = pageSize.isPresent() && pageSize.get()<20 && pageSize.get()>10 ?pageSize.get() : 10;
		int _pageNum = pageNum.isPresent() && pageNum.get()>0 ? pageNum.get(): 1;
		Pageable pageable = PageRequest.of(_pageNum-1, _pageSize);
		
		String managerEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		int companyId = this.getCompanyIdByManagerEmail(managerEmail).getData();
		DataResult<List<CarRentalListDto>> requestResult = this.carRentalService.getRentalRequestsByCompanyId(companyId, pageable);
		
		if(requestResult.getSuccess()) {
			log.info("Rental requests listed by "+ managerEmail);
			return new SuccessDataResult<List<CarRentalListDto>>(requestResult.getData());
		}
		else {
			log.error("An error occurred while listing car rental requests. Error: "+ requestResult.getMessage());
			return new ErrorDataResult<List<CarRentalListDto>>(requestResult.getMessage());
		}
		
	}

	@Override
	public Result rejectRentalRequest(int requestId) {
		String managerEmail =  SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		int companyId = this.getCompanyIdByManagerEmail(managerEmail).getData();
		DataResult<String> customerEmail = this.carRentalService.getCustomerEmailByRequestId(requestId);
		this.emailSenderService.sendEmail(customerEmail.getData(), Messages.carRentalRequestRejectedCustomerMessage, Messages.carRentalRequestRejectedCustomerSubject);
		Result result = this.carRentalService.deleteRentalRequestById(requestId,companyId);
		log.info("A rental requests rejected by "+ managerEmail);
		return result;
	}

	@Override
	public Result confirmRentalRequest(int requestId) {
		String managerEmail =  SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		int companyId = this.getCompanyIdByManagerEmail(managerEmail).getData();
		
		Company company = this.companyService.getById(companyId).getData();
		company.setPoint(company.getPoint() +CompanyPoints.CompanyPointIncreaseValue);
		Result companyPointResult =this.companyService.save(company);
		
		if(companyPointResult.getSuccess()) {
			log.info(company.getCompanyName()+ "'s rating increased by "+CompanyPoints.CompanyPointIncreaseValue);
		}
		
		DataResult<String> customerEmail = this.carRentalService.getCustomerEmailByRequestId(requestId);
		this.emailSenderService.sendEmail(customerEmail.getData(), Messages.carRentalRequestApprovedCustomerMessage, Messages.carRentalRequestApprovedCustomerSubject);
		Result result = this.carRentalService.confirmRentalRequestById(requestId,companyId);
		log.info("A rental requests corfirmed by "+ managerEmail);
		return result;
	}

	@Override
	public Result returnVehicle(int rentalId) {
		String managerEmail =  SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		int companyId = this.getCompanyIdByManagerEmail(managerEmail).getData();
		Result result = this.carRentalService.returnVehicle(rentalId, companyId);
		log.info("Car returned by "+managerEmail);
		return result;
	}

	
	


}
