package com.mos.moskit.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mos.moskit.domain.dao.DatabaseConnectionRepository;
import com.mos.moskit.domain.entity.DatabaseConnection;
import com.mos.moskit.web.generic.ApiVersion;
import com.mos.moskit.web.generic.GenericCrudRestController;

@RestController
@RequestMapping("/connections")
@ApiVersion({ 1, 2 })
public class DatabaseConnectionRest extends GenericCrudRestController<DatabaseConnection> {

	@Autowired
	public DatabaseConnectionRest(DatabaseConnectionRepository repository) {
		super(repository);
	}

}
