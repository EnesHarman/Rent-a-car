package com.webproje.arackiralama.Business.abstracts;

import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Entity.dto.customerDtos.CustomerRegisterDto;

public interface CustomerService {

	Result register(CustomerRegisterDto customerRegisterDto);

}
