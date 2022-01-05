package com.webproje.arackiralama.Business.concretes;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.webproje.arackiralama.Business.abstracts.CarRentalService;
import com.webproje.arackiralama.Business.constants.Messages;
import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Core.utilities.result.concretes.ErrorResult;
import com.webproje.arackiralama.Core.utilities.result.concretes.SuccessDataResult;
import com.webproje.arackiralama.Core.utilities.result.concretes.SuccessResult;
import com.webproje.arackiralama.Entity.concretes.CarRentals;
import com.webproje.arackiralama.Entity.concretes.Vehicle;
import com.webproje.arackiralama.Entity.concretes.VehicleStatus;
import com.webproje.arackiralama.Entity.dto.carRentalsDtos.CarRentalListDto;
import com.webproje.arackiralama.Repository.CarRentalRepository;
import com.webproje.arackiralama.Repository.VehicleRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CarRentalManager  implements CarRentalService{

	private final CarRentalRepository carRentalRepository;
	private final VehicleRepository vehicleRepository;

	public CarRentalManager(CarRentalRepository carRentalRepository,VehicleRepository vehicleRepository) {
		super();
		this.carRentalRepository = carRentalRepository;
		this.vehicleRepository = vehicleRepository;
	}

	@Override
	public Result addCarRentalRequest(CarRentals rentalRequest) {
		this.carRentalRepository.save(rentalRequest);
		log.info("A customer with "+ rentalRequest.getCustomer().getUser().getEmail() +"email has sent a rental request to "+ rentalRequest.getCompany().getCompanyName());;
		return new SuccessResult(Messages.carRentalRequestReceived);
	}

	@Override
	public DataResult<List<CarRentalListDto>> getRentalRequestsByCompanyId(int companyId, Pageable pageable) {
		List<CarRentalListDto> rentalRequests = this.carRentalRepository.getRentalRequestsByCompanyId(companyId, pageable);
		return new SuccessDataResult<List<CarRentalListDto>>(rentalRequests);
	}

	@Override
	public Result deleteRentalRequestById(int requestId, int companyId) {
		CarRentals carRental = this.carRentalRepository.getById(requestId);
		
		if(carRental.getCompany().getId() != companyId) {
			return new ErrorResult(Messages.otherCompaniesActionError);
		}
		
		if(carRental.getIsApproved()) {
			return new ErrorResult(Messages.carRentalCannotReject);
		}
		
		this.carRentalRepository.deleteById(requestId);
		
		log.info("A rental request deleted by "+ carRental.getCompany().getCompanyName() );
		return new SuccessResult(Messages.carRentalRequestRejected);
	}

	@Override
	public Result confirmRentalRequestById(int requestId, int companyId) {
		CarRentals carRental = this.carRentalRepository.getById(requestId);
		if(carRental.getCompany().getId() != companyId) {
			return new ErrorResult(Messages.otherCompaniesActionError);
		}
		if(carRental.getIsApproved()) {
			return new ErrorResult(Messages.carRentalAlreadyApproved);
		}
		
		carRental.setApproved(true);
		this.carRentalRepository.save(carRental);
		
		int vehicleId = carRental.getVehicle().getId(); 
		Vehicle vehicle = this.vehicleRepository.getById(vehicleId);
		vehicle.setVehicleStatus(new VehicleStatus(3,"Rented"));
		this.vehicleRepository.save(vehicle);
		log.info("A rental request confirmed by "+ carRental.getCompany().getCompanyName() );
		return new SuccessResult(Messages.carRentalRequestApproved);
	}

	@Override
	public Result returnVehicle(int rentalId, int companyId) {
		CarRentals carRental = this.carRentalRepository.getById(rentalId);
		
		if(carRental.getCompany().getId() != companyId) {
			return new ErrorResult(Messages.otherCompaniesActionError);
		}
		
		int vehicleId = carRental.getVehicle().getId(); 
		Vehicle vehicle = this.vehicleRepository.getById(vehicleId);
		vehicle.setVehicleStatus(new VehicleStatus(1,"Rentable"));
		this.vehicleRepository.save(vehicle);
		
		this.carRentalRepository.deleteById(rentalId); 
		log.info("A vehicle returned to "+ carRental.getCompany().getCompanyName() );
		return new SuccessResult(Messages.vehicleReturned);
	}

	@Override
	public DataResult<String> getCustomerEmailByRequestId(int requestId) {
		CarRentals carRental = this.carRentalRepository.getById(requestId);
		return new SuccessDataResult<String>("User email.",carRental.getCustomer().getUser().getEmail());
	}	
}
