package com.webproje.arackiralama.Business.abstracts;

import java.util.List;

import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Entity.concretes.Company;
import com.webproje.arackiralama.Entity.dto.companyDtos.CompanyAddDto;

public interface CompanyService {

	Result addCompany(CompanyAddDto companyAddDto);

	Result deleteCompany(int companyId);

	DataResult<List<Company>> listCompany();

}
