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
import com.webproje.arackiralama.Business.constants.Messages;
import com.webproje.arackiralama.Core.entity.concretes.AppUser;
import com.webproje.arackiralama.Core.entity.concretes.Role;
import com.webproje.arackiralama.Core.utilities.emailSender.EmailSenderService;
import com.webproje.arackiralama.Core.utilities.emailSender.EmailSenderServiceImp;
import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Core.utilities.result.concretes.ErrorDataResult;
import com.webproje.arackiralama.Core.utilities.result.concretes.SuccessDataResult;
import com.webproje.arackiralama.Core.utilities.result.concretes.SuccessResult;
import com.webproje.arackiralama.Entity.concretes.Company;
import com.webproje.arackiralama.Entity.concretes.CompanyManager;
import com.webproje.arackiralama.Entity.dto.carRentalsDtos.CarRentalListDto;
import com.webproje.arackiralama.Entity.dto.companyManagerDtos.CompanyManagerRegisterDto;
import com.webproje.arackiralama.Repository.CompanyManagerRepository;

@Service
public class CompanyManagerManager implements CompanyManagerService{

	private final CompanyManagerRepository companyManagerRepository;
	private final AppUserService appUserService;
	private final CarRentalService carRentalService;
	private final EmailSenderService emailSenderService;

	
	@Autowired
	public CompanyManagerManager(CompanyManagerRepository companyManagerRepository,AppUserService appUserService, CarRentalService carRentalService,EmailSenderService emailSenderService) {
		super();
		this.companyManagerRepository = companyManagerRepository;
		this.appUserService = appUserService;
		this.carRentalService = carRentalService;
		this.emailSenderService = emailSenderService;
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
		companyManager.setAppUser(insertedUser);
		this.companyManagerRepository.save(companyManager);
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
			return new SuccessDataResult<List<CarRentalListDto>>(requestResult.getData());
		}
		else {
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
		return result;
	}

	@Override
	public Result confirmRentalRequest(int requestId) {
		String managerEmail =  SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		int companyId = this.getCompanyIdByManagerEmail(managerEmail).getData();
		DataResult<String> customerEmail = this.carRentalService.getCustomerEmailByRequestId(requestId);
		this.emailSenderService.sendEmail(customerEmail.getData(), Messages.carRentalRequestApprovedCustomerMessage, Messages.carRentalRequestApprovedCustomerSubject);
		Result result = this.carRentalService.confirmRentalRequestById(requestId,companyId);
		return result;
	}

	@Override
	public Result returnVehicle(int rentalId) {
		String managerEmail =  SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		int companyId = this.getCompanyIdByManagerEmail(managerEmail).getData();
		Result result = this.carRentalService.returnVehicle(rentalId, companyId);
		
		return result;
	}
	


}
