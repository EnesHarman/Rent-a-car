package com.webproje.arackiralama.Business.abstracts;

import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Entity.dto.VehicleDto;

public interface VehicleService {

	Result addVehicle(VehicleDto vehicleDto);

	Result updateVehicle(int vehicleId, VehicleDto vehicleDto);

}
