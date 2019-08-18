package com.mos.moskit.domain.dao;

import org.springframework.stereotype.Repository;

import com.mos.moskit.domain.entity.account.User;

@Repository
public interface UserRepository extends BaseEntityRepository<User> {

	User findByLogin(String login);
}
