package com.mos.moskit.web.api.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mos.moskit.domain.dao.UserGroupRepository;
import com.mos.moskit.domain.entity.account.UserGroup;
import com.mos.moskit.web.api.ApiVersions;
import com.mos.moskit.web.generic.ApiVersion;
import com.mos.moskit.web.generic.JpaCrudRestController;

@RestController
@RequestMapping("/user_groups")
@ApiVersion({ ApiVersions.API_1 })
public class UserGroupRestController extends JpaCrudRestController<UserGroup> {

	@Autowired
	public UserGroupRestController(UserGroupRepository repository) {
		super(repository);
	}

}
