package com.mos.moskit.web.generic;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.mos.moskit.common.jpa.BaseEntity;
import com.mos.moskit.domain.dao.BaseEntityRepository;
import com.mos.moskit.domain.entity.account.Role;
import com.mos.moskit.service.security.HasRole;

public abstract class GenericCrudRestController<ENTITY extends BaseEntity> {

	private static final String ID = "/{id}";
	private final BaseEntityRepository<ENTITY> repository;

	public GenericCrudRestController(BaseEntityRepository<ENTITY> repository) {
		this.repository = repository;
	}

	@PostMapping
	public ENTITY save(ENTITY entity) {
		return repository.save(entity);
	}

	@PutMapping(ID)
	public ENTITY save(@PathVariable long id, ENTITY entity) {
		return repository.save(entity);
	}

	@HasRole(Role.ADMIN)
	@GetMapping(ID)
	public ENTITY findOne(@PathVariable long id) {
		return repository.findById(id).orElse(null);
	}

	@HasRole(Role.ADMIN)
	@HasRole(Role.DEVELOPER)
	@GetMapping
	public List<ENTITY> findAll() {
		return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
	}

	@DeleteMapping(ID)
	public void delete(@PathVariable long id) {
		repository.deleteById(id);
	}

}
