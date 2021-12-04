package com.webproje.arackiralama.Business.abstracts;

import java.util.List;

import com.webproje.arackiralama.Core.entity.concretes.Role;
import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;


public interface RoleService {
	Result addRole(Role role);

	Result deleteRole(int roleId);

	Result updateRole(Role role);

	DataResult<List<Role>> listRoles();
}
