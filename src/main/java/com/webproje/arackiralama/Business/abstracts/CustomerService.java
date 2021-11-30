package com.webproje.arackiralama.Business.abstracts;

import com.webproje.arackiralama.Core.entity.concretes.AppUser;
import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Entity.concretes.Customer;
import com.webproje.arackiralama.Entity.dto.customerDtos.CustomerRegisterDto;

public interface CustomerService {

	Result register(CustomerRegisterDto customerRegisterDto);

	DataResult<Customer> getCustomerByEmail(String customerEmail);

}
