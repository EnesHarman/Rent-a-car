package com.webproje.arackiralama.Business.abstracts;

import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Entity.concretes.CarRentals;

public interface CarRentalService {

	Result addCarRentalRequest(CarRentals rentalRequest);

}
