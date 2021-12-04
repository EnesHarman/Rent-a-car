package com.webproje.arackiralama.Business.abstracts;

import java.util.List;

import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Entity.concretes.VehicleStatus;

public interface VehicleStatusService {

	DataResult<VehicleStatus> getByName(String name);

	Result addVehicleStatus(VehicleStatus vehicleStatus);

	Result updateVehicleStatus(VehicleStatus vehicleStatus);

	Result deleteVehicleStatus(int id);

	DataResult<List<VehicleStatus>> listVehicleStatus();

}
