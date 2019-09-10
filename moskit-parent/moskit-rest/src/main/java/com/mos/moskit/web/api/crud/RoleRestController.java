package com.mos.moskit.web.api.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mos.moskit.domain.dao.RoleRepository;
import com.mos.moskit.domain.entity.account.AbstractRole;
import com.mos.moskit.web.api.ApiVersions;
import com.mos.moskit.web.generic.ApiVersion;
import com.mos.moskit.web.generic.JpaCrudRestController;

@RestController
@RequestMapping("/roles")
@ApiVersion({ ApiVersions.API_1 })
public class RoleRestController extends JpaCrudRestController<AbstractRole> {

	@Autowired
	public RoleRestController(RoleRepository repository) {
		super(repository);
	}

}
