package com.webproje.arackiralama.Business.abstracts;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Entity.concretes.CarRentals;
import com.webproje.arackiralama.Entity.dto.carRentalsDtos.CarRentalListDto;

public interface CarRentalService {

	Result addCarRentalRequest(CarRentals rentalRequest);

	DataResult<List<CarRentalListDto>> getRentalRequestsByCompanyId(int companyId, Pageable pageable);

	Result deleteRentalRequestById(int requestId, int companyId);

	Result confirmRentalRequestById(int requestId, int companyId);

	Result returnVehicle(int rentalId, int companyId);

	DataResult<String> getCustomerEmailByRequestId(int requestId);
	

}
