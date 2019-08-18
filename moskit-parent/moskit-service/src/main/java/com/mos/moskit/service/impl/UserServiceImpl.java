package com.mos.moskit.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mos.moskit.common.jpa.EntityStatus;
import com.mos.moskit.domain.dao.RoleRepository;
import com.mos.moskit.domain.dao.UserRepository;
import com.mos.moskit.domain.entity.account.AbstractRole;
import com.mos.moskit.domain.entity.account.Role;
import com.mos.moskit.domain.entity.account.User;
import com.mos.moskit.service.SecurityService;
import com.mos.moskit.service.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private SecurityService securityService;

	@Override
	@Transactional
	public User createNewUser(User user) {
		User newUser = new User();
		newUser.setLogin(user.getLogin());
		newUser.setPassword(securityService.encryptPassword(user.getPassword()));
		newUser.setEntityStatus(EntityStatus.ACTIVE);
		return userRepository.save(newUser);
	}

	@Override
	@Transactional
	public void assignRoleToUser(User user, Role roleType) {
		AbstractRole role = roleRepository.findByUserAndDiscriminator(user, roleType);
		if (role == null) {
			role = roleType.createNew();
			role.setUser(user);
		}
		role.setEntityStatus(EntityStatus.ACTIVE);
		roleRepository.save(role);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByLogin(username);
	}
}
