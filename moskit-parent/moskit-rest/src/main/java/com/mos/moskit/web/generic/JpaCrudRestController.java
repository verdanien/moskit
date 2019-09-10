package com.mos.moskit.web.generic;

import java.util.Objects;

import com.mos.moskit.common.jpa.BaseEntity;
import com.mos.moskit.domain.dao.BaseEntityRepository;

public abstract class JpaCrudRestController<ENTITY extends BaseEntity> extends CrudRestController<ENTITY> {

	public JpaCrudRestController(BaseEntityRepository<ENTITY> repository) {
		this(new JpaCrudService<>(Objects.requireNonNull(repository)));
	}

	public JpaCrudRestController(JpaCrudService<ENTITY> crudService) {
		super(Objects.requireNonNull(crudService));
	}

}
