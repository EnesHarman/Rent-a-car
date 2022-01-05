package com.webproje.arackiralama.Business.abstracts;

import java.util.List;
import java.util.Optional;

import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Entity.concretes.Company;
import com.webproje.arackiralama.Entity.dto.companyDtos.CompanyAddDto;
import com.webproje.arackiralama.Entity.dto.companyDtos.CompanyListDto;


public interface CompanyService {

	Result addCompany(CompanyAddDto companyAddDto);

	Result deleteCompany(int companyId);

	DataResult<List<CompanyListDto>> listCompany(Optional<Integer> companyId,Optional<Integer> pageSize, Optional<Integer> pageNum);

	DataResult<Company> getById(int companyId);

	Result save(Company company);


}