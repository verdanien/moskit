package com.mos.moskit.web.api.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mos.moskit.domain.dao.UserRepository;
import com.mos.moskit.domain.entity.account.User;
import com.mos.moskit.web.api.ApiVersions;
import com.mos.moskit.web.generic.ApiVersion;
import com.mos.moskit.web.generic.GenericCrudRestController;

@RestController
@RequestMapping("/users")
@ApiVersion({ ApiVersions.API_1 })
public class UserRestController extends GenericCrudRestController<User> {

	@Autowired
	public UserRestController(UserRepository repository) {
		super(repository);
	}

}
