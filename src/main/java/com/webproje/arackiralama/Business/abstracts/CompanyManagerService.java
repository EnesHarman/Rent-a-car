package com.webproje.arackiralama.Business.abstracts;


import java.util.List;
import java.util.Optional;

import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;

import com.webproje.arackiralama.Entity.dto.carRentalsDtos.CarRentalListDto;
import com.webproje.arackiralama.Entity.dto.companyManagerDtos.CompanyManagerRegisterDto;


public interface CompanyManagerService {

	DataResult<Integer> getCompanyIdByManagerEmail(String email);
	DataResult<List<CarRentalListDto>> listCarRentalRequests(Optional<Integer> pageSize, Optional<Integer> pageNum);
	Result rejectRentalRequest(int requestId);
	Result confirmRentalRequest(int requestId);
	Result returnVehicle(int rentalId);
	Result register(CompanyManagerRegisterDto companyManagerRegisterDto);

}
