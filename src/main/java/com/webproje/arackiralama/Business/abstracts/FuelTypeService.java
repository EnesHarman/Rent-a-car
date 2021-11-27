package com.webproje.arackiralama.Business.abstracts;

import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Entity.concretes.FuelType;

public interface FuelTypeService {

	DataResult<FuelType> getByName(String fuelType);

}
