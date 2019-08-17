package com.mos.moskit.web.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mos.moskit.domain.dao.DatabaseConnectionRepository;
import com.mos.moskit.domain.entity.DatabaseConnection;
import com.mos.moskit.web.generic.GenericCrudRestController;

@RestController
@RequestMapping("/connections")
public class DatabaseConnectionRest extends GenericCrudRestController<DatabaseConnection> {

	@Autowired
	public DatabaseConnectionRest(DatabaseConnectionRepository repository) {
		super(repository);
	}

}
