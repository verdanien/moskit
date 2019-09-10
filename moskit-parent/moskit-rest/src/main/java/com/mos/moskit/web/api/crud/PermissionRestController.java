package com.mos.moskit.web.api.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mos.moskit.domain.dao.PermissionRepository;
import com.mos.moskit.domain.entity.permission.Permission;
import com.mos.moskit.web.api.ApiVersions;
import com.mos.moskit.web.generic.ApiVersion;
import com.mos.moskit.web.generic.JpaCrudRestController;

@RestController
@RequestMapping("/permissions")
@ApiVersion({ ApiVersions.API_1 })
public class PermissionRestController extends JpaCrudRestController<Permission> {

	@Autowired
	public PermissionRestController(PermissionRepository repository) {
		super(repository);
	}

}
