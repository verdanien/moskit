package com.mos.moskit.web.generic;

import java.util.List;
import java.util.Optional;

public interface CrudService<ENTITY> {

	ENTITY save(ENTITY entity);

	Optional<ENTITY> findById(long id);

	List<ENTITY> findAll();

	void deleteById(long id);

}
