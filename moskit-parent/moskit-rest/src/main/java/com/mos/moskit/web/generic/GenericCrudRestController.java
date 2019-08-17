package com.mos.moskit.web.generic;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.mos.moskit.common.jpa.BaseEntity;
import com.mos.moskit.domain.dao.BaseEntityRepository;

//@RestController()
//@RequestMapping(value = "/{entity}", produces = MediaType.APPLICATION_JSON_VALUE)
public abstract class GenericCrudRestController<ENTITY extends BaseEntity> {

	private static final String ACCEPT_APPLICATION_JSON = "Accept=" + MediaType.APPLICATION_JSON_VALUE;
	private final BaseEntityRepository<ENTITY> repository;

	public GenericCrudRestController(BaseEntityRepository<ENTITY> repository) {
		this.repository = repository;
	}

	@PostMapping
	public ENTITY save(ENTITY entity) {
		return repository.save(entity);
	}

	@PutMapping("/{id}")
	public ENTITY save(@PathVariable long id, ENTITY entity) {
		return repository.save(entity);
	}

	@GetMapping("/{id}")
	public ENTITY findOne(@PathVariable long id) {
		return repository.findById(id).orElse(null);
	}

	@GetMapping("")
	public List<ENTITY> findAll() {
		return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		repository.deleteById(id);
	}

}
