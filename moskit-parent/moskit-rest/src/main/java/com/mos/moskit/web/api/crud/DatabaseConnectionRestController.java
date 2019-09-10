package com.mos.moskit.web.api.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mos.moskit.domain.dao.DatabaseConnectionRepository;
import com.mos.moskit.domain.entity.DatabaseConnection;
import com.mos.moskit.web.api.ApiVersions;
import com.mos.moskit.web.generic.ApiVersion;
import com.mos.moskit.web.generic.JpaCrudRestController;

@RestController
@RequestMapping("/connections")
@ApiVersion({ ApiVersions.API_1, 2 })
public class DatabaseConnectionRestController extends JpaCrudRestController<DatabaseConnection> {

	@Autowired
	public DatabaseConnectionRestController(DatabaseConnectionRepository repository) {
		super(repository);
	}

}
