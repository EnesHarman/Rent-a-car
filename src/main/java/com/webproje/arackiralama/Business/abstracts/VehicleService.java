package com.webproje.arackiralama.Business.abstracts;

import java.util.List;

import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Entity.dto.vehicleDtos.VehicleDto;

public interface VehicleService {

	Result addVehicle(VehicleDto vehicleDto);

	Result updateVehicle(int vehicleId, VehicleDto vehicleDto);

	DataResult<List<VehicleDto>> listVehiclesForManager();

}
