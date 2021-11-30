package com.webproje.arackiralama.Business.concretes;

import org.springframework.stereotype.Service;

import com.webproje.arackiralama.Business.abstracts.CarRentalService;
import com.webproje.arackiralama.Business.constants.Messages;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Core.utilities.result.concretes.SuccessDataResult;
import com.webproje.arackiralama.Core.utilities.result.concretes.SuccessResult;
import com.webproje.arackiralama.Entity.concretes.CarRentals;
import com.webproje.arackiralama.Repository.CarRentalRepository;

@Service
public class CarRentalManager  implements CarRentalService{

	private final CarRentalRepository carRentalRepository;

	public CarRentalManager(CarRentalRepository carRentalRepository) {
		super();
		this.carRentalRepository = carRentalRepository;
	}

	@Override
	public Result addCarRentalRequest(CarRentals rentalRequest) {
		this.carRentalRepository.save(rentalRequest);
		return new SuccessResult(Messages.carRentalRequestReceived);
	}
	
	
	
}
