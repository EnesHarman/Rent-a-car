package com.webproje.arackiralama.Business.abstracts;

import java.util.List;

import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Entity.concretes.FuelType;

public interface FuelTypeService {

	DataResult<FuelType> getByName(String fuelType);

	Result addFuelType(FuelType fuelType);

	Result deleteFuelType(int fuelTypeId);

	Result updateFuelType(FuelType fuelType);

	DataResult<List<FuelType>> listFuelTypes();

}
