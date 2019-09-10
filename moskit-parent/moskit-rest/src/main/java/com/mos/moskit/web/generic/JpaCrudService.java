package com.mos.moskit.web.generic;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.mos.moskit.common.jpa.BaseEntity;
import com.mos.moskit.domain.dao.BaseEntityRepository;

public class JpaCrudService<ENTITY extends BaseEntity> implements CrudService<ENTITY> {

	private final BaseEntityRepository<ENTITY> repository;

	public JpaCrudService(BaseEntityRepository<ENTITY> repository) {
		this.repository = repository;
	}

	@Override
	public ENTITY save(ENTITY entity) {
		return repository.save(entity);
	}

	@Override
	public Optional<ENTITY> findById(long id) {
		return repository.findById(id);
	}

	@Override
	public List<ENTITY> findAll() {
		return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
	}

	@Override
	public void deleteById(long id) {
		repository.deleteById(id);
	}

}
