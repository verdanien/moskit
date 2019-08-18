package com.mos.moskit.web;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.mos.moskit.domain.dao.UserRepository;
import com.mos.moskit.domain.entity.account.Role;
import com.mos.moskit.domain.entity.account.User;
import com.mos.moskit.service.UserService;

@Component
public class TempDataInitializer {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@PostConstruct
	public void initialize() {
		if (checkIfAlreadyInitialized()) {
			User user = new User();
			user.setLogin("admin");
			user.setPassword("admin");
			user = userService.createNewUser(user);
			userService.assignRoleToUser(user, Role.ADMIN);

			user = new User();
			user.setLogin("dev");
			user.setPassword("dev");
			user = userService.createNewUser(user);
			userService.assignRoleToUser(user, Role.DEVELOPER);
		}

	}

	private boolean checkIfAlreadyInitialized() {
		return userRepository.findByLogin("admin") == null;
	}

}
