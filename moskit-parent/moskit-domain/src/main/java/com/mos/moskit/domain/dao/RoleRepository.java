package com.mos.moskit.domain.dao;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mos.moskit.domain.entity.account.AbstractRole;
import com.mos.moskit.domain.entity.account.Role;
import com.mos.moskit.domain.entity.account.User;

@Repository
public interface RoleRepository extends BaseEntityRepository<AbstractRole> {

	AbstractRole findByUserAndDiscriminator(@Param("user") User user, @Param("role") Role discriminator);

}
