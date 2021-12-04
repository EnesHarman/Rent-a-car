package com.webproje.arackiralama.Business.concretes;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.webproje.arackiralama.Business.constants.Messages;
import com.webproje.arackiralama.Business.abstracts.AppUserService;
import com.webproje.arackiralama.Business.abstracts.CustomerService;
import com.webproje.arackiralama.Core.entity.concretes.AppUser;
import com.webproje.arackiralama.Core.entity.concretes.Role;
import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Core.utilities.result.concretes.SuccessDataResult;
import com.webproje.arackiralama.Core.utilities.result.concretes.SuccessResult;
import com.webproje.arackiralama.Entity.concretes.Customer;
import com.webproje.arackiralama.Entity.dto.carRentalsDtos.CarRentalListDto;
import com.webproje.arackiralama.Entity.dto.customerDtos.CustomerRegisterDto;
import com.webproje.arackiralama.Repository.CarRentalRepository;
import com.webproje.arackiralama.Repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerManager implements CustomerService{

	private final CustomerRepository customerRepository;
	private final AppUserService appUserService;
	private final CarRentalRepository carRentalRepository;
	
	@Autowired
	public CustomerManager(CustomerRepository customerRepository, AppUserService appUserService,CarRentalRepository carRentalRepository) {
		super();
		this.customerRepository = customerRepository;
		this.appUserService = appUserService;
		this.carRentalRepository = carRentalRepository;
	}

	@Override
	public Result register(CustomerRegisterDto customerRegisterDto) {
		AppUser user = new AppUser();
		user.setEmail(customerRegisterDto.getEmail());
		user.setPassword(customerRegisterDto.getPassword());
		user.setRole(new Role(2,"ROLE_CUSTOMER"));
		AppUser insertedUser = this.appUserService.saveUser(user);
		
		Customer customer = new Customer();
		customer.setName(customerRegisterDto.getName());
		customer.setSurname(customerRegisterDto.getSurname());
		customer.setIdentityNumber(customerRegisterDto.getIdentityNumber());
		customer.setPhoneNumber(customerRegisterDto.getPhoneNumber());
		customer.setUser(insertedUser);
		
		this.customerRepository.save(customer);
		log.info("A customer registered to the service with "+ customer.getUser().getEmail()+ " email.");
		return new SuccessResult(Messages.customerRegistered);
	}

	@Override
	public DataResult<Customer> getCustomerByEmail(String customerEmail) {
		AppUser user = this.appUserService.getUserByEmail(customerEmail).getData();
		Customer customer = this.customerRepository.getByUser_UserId(user.getUserId());
		log.info("Customer with "+user.getEmail()+ " email has been listed.");
		return new SuccessDataResult<Customer>(customer);
	}

	@Override
	public DataResult<List<CarRentalListDto>> listRentalRequests(Optional<Integer> pageSize, Optional<Integer> pageNum) {
		int _pageSize = pageSize.isPresent() && pageSize.get()<20 && pageSize.get()>10 ?pageSize.get() : 10;
		int _pageNum = pageNum.isPresent() && pageNum.get()>0 ? pageNum.get(): 1;
		Pageable pageable = PageRequest.of(_pageNum-1, _pageSize);
		
		String customerEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		Customer customer = this.getCustomerByEmail(customerEmail).getData();
		List<CarRentalListDto> carRentalListDto = this.carRentalRepository.getRentalRequestsByCustomerId(customer.getId(), pageable);
		
		log.info("The customer with "+customerEmail+" email has listed his/her rental requests.");
		return new SuccessDataResult<List<CarRentalListDto>>(carRentalListDto);
	}

	@Override
	public DataResult<CarRentalListDto> listSingleRentalRequest(int rentalId) {
		CarRentalListDto carRentalListDto = this.carRentalRepository.listSingleRentalRequest(rentalId);
		return new SuccessDataResult<CarRentalListDto>(carRentalListDto);
	}
	

}
