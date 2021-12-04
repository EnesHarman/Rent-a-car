package com.webproje.arackiralama.Business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webproje.arackiralama.Business.abstracts.FuelTypeService;
import com.webproje.arackiralama.Business.constants.Messages;
import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Core.utilities.result.concretes.ErrorDataResult;
import com.webproje.arackiralama.Core.utilities.result.concretes.SuccessDataResult;
import com.webproje.arackiralama.Core.utilities.result.concretes.SuccessResult;
import com.webproje.arackiralama.Entity.concretes.FuelType;
import com.webproje.arackiralama.Repository.FuelTypeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FuelTypeManager implements FuelTypeService{

	private final FuelTypeRepository fuelTypeRepository;
	
	@Autowired
	public FuelTypeManager(FuelTypeRepository fuelTypeRepository) {
		super();
		this.fuelTypeRepository = fuelTypeRepository;
	}
	
	@Override
	public DataResult<FuelType> getByName(String fuelType) {
		FuelType fType= this.fuelTypeRepository.getByName(fuelType);
		if(fType!=null) {
			return new SuccessDataResult<FuelType>(fType);
		}
		else {
			return new ErrorDataResult<FuelType>(Messages.fuelTypeNotFound);
		}
	}

	@Override
	public Result addFuelType(FuelType fuelType) {
		this.fuelTypeRepository.save(fuelType);
		log.info("A fuel type has added to the service with "+ fuelType.getName()+ " name.");
		return new SuccessResult(Messages.fuelTypeAdded);
	}

	@Override
	public Result deleteFuelType(int fuelTypeId) {
		this.fuelTypeRepository.deleteById(fuelTypeId);
		log.info("A fuel type has deleted from the service with "+ fuelTypeId+ " id.");
		return new SuccessResult(Messages.fuelTypeDeleted);
	}

	@Override
	public Result updateFuelType(FuelType fuelType) {
		FuelType _fuelType = this.fuelTypeRepository.getById(fuelType.getId());
		log.info("A fuel type's name has been updated to"+fuelType.getName()+" from "+ _fuelType.getName());
		_fuelType.setName(fuelType.getName());
		this.fuelTypeRepository.save(_fuelType);
		return new SuccessResult(Messages.fuelTypeUpdated);
	}

	@Override
	public DataResult<List<FuelType>> listFuelTypes() {
		log.info("Fuel types have been listed.");
		return new SuccessDataResult<List<FuelType>>(this.fuelTypeRepository.findAll());
	}


	

}
