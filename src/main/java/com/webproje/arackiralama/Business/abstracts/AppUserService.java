package com.webproje.arackiralama.Business.abstracts;

import com.webproje.arackiralama.Core.entity.concretes.AppUser;
import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;

public interface AppUserService {
	public AppUser saveUser(AppUser user);
	public DataResult<AppUser> getUserByEmail(String email);
}
