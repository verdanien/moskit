package com.mos.moskit.service;

import com.mos.moskit.domain.entity.account.Role;
import com.mos.moskit.domain.entity.account.User;

public interface UserService {

	User createNewUser(User user);

	void assignRoleToUser(User user, Role roleType);

}
