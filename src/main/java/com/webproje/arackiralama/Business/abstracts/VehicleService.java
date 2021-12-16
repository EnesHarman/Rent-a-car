package com.webproje.arackiralama.Business.abstracts;

import java.util.List;
import java.util.Optional;


import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Entity.dto.carRentalsDtos.CarRentalDto;
import com.webproje.arackiralama.Entity.dto.vehicleDtos.VehicleDto;

public interface VehicleService {

	Result addVehicle(VehicleDto vehicleDto);

	Result updateVehicle(int vehicleId, VehicleDto vehicleDto);

	DataResult<List<VehicleDto>> listVehiclesForManager(Optional<Integer> pageSize,Optional<Integer> pageNum);

	Result deleteVehicle(int vehicleId);

	DataResult<List<VehicleDto>> listVehicles(Optional<Integer> companyId,Optional<Integer> cityId, Optional<Integer> pageSize,
			Optional<Integer> pageNum);

	Result rentACar(CarRentalDto carRentalDto);

	DataResult<VehicleDto> listSingleVehicle(Optional<Integer> vehicleId);

}
