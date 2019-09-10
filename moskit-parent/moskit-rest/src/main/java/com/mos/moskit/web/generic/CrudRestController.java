package com.mos.moskit.web.generic;

import java.util.List;
import java.util.Objects;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mos.moskit.service.security.HasAuthority;

public abstract class CrudRestController<ENTITY> {

	private static final String ID = "/{id}";
	private final CrudService<ENTITY> crudService;

	public CrudRestController(CrudService<ENTITY> crudService) {
		this.crudService = Objects.requireNonNull(crudService);
	}

	@HasAuthority("create")
	@PostMapping
	public ENTITY save(@RequestBody ENTITY entity) {
		return crudService.save(entity);
	}

	@HasAuthority("update")
	@PutMapping(ID)
	public ENTITY save(@RequestBody ENTITY entity, @PathVariable long id) {
		return crudService.save(entity);
	}

	@HasAuthority("read")
//	@HasRole(Role.ADMIN)
	@GetMapping(ID)
	public ENTITY findOne(@PathVariable long id) {
		return crudService.findById(id).orElse(null);
	}

	@HasAuthority("read")
//	@HasRole(Role.ADMIN)
//	@HasRole(Role.DEVELOPER)
	@GetMapping
	public List<ENTITY> findAll() {
		return crudService.findAll();
	}

	@HasAuthority("delete")
	@DeleteMapping(ID)
	public void delete(@PathVariable long id) {
		crudService.deleteById(id);
	}

}
