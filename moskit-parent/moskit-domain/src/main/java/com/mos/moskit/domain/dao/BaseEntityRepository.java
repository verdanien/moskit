package com.mos.moskit.domain.dao;

import org.springframework.data.repository.CrudRepository;

import com.mos.moskit.common.jpa.BaseEntity;

public interface BaseEntityRepository<T extends BaseEntity> extends CrudRepository<T, Long> {

}
