package com.webproje.arackiralama.Business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webproje.arackiralama.Business.constants.Messages;
import com.webproje.arackiralama.Business.abstracts.AppUserService;
import com.webproje.arackiralama.Business.abstracts.CustomerService;
import com.webproje.arackiralama.Core.entity.concretes.AppUser;
import com.webproje.arackiralama.Core.entity.concretes.Role;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Core.utilities.result.concretes.SuccessResult;
import com.webproje.arackiralama.Entity.concretes.Customer;
import com.webproje.arackiralama.Entity.dto.CustomerRegisterDto;
import com.webproje.arackiralama.Repository.CustomerRepository;

@Service
public class CustomerManager implements CustomerService{

	private final CustomerRepository customerRepository;
	private final AppUserService appUserService;
	
	@Autowired
	public CustomerManager(CustomerRepository customerRepository, AppUserService appUserService) {
		super();
		this.customerRepository = customerRepository;
		this.appUserService = appUserService;
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
		return new SuccessResult(Messages.customerRegistered);
	}

}
