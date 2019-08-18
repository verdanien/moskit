package com.mos.moskit.domain.entity.account;

import java.util.function.Supplier;

public enum Role {
	ADMIN(AdminRole::new),
	DEVELOPER(DeveloperRole::new);

	private final Supplier<AbstractRole> roleFactory;

	private Role(Supplier<AbstractRole> roleFactory) {
		this.roleFactory = roleFactory;
	}

	@SuppressWarnings("unchecked")
	public <ROLE extends AbstractRole> ROLE createNew() {
		return (ROLE) roleFactory.get();
	}

	public static final class Values {
		public static final String ADMIN = "ADMIN";
		public static final String DEVELOPER = "DEVELOPER";
	}
}
