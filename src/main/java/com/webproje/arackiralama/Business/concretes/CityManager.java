package com.webproje.arackiralama.Business.concretes;

import java.util.List;

import org.springframework.stereotype.Service;

import com.webproje.arackiralama.Business.abstracts.CityService;
import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.concretes.SuccessDataResult;
import com.webproje.arackiralama.Entity.concretes.City;
import com.webproje.arackiralama.Repository.CityRepository;

@Service
public class CityManager implements CityService{

	private final CityRepository cityRepository;
	
	
	public CityManager(CityRepository cityRepository) {
		super();
		this.cityRepository = cityRepository;
	}


	@Override
	public DataResult<List<City>> listCities() {
		return new SuccessDataResult<List<City>>(this.cityRepository.findAll());
	}

}
