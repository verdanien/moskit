package com.mos.moskit.common.jpa;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class BaseAuditableEntityListener {

	@PreUpdate
	@PrePersist
	public void preSave(Object object) {
		if (object instanceof BaseAuditableEntity) {
			if (((BaseAuditableEntity) object).getEntityStatus() == null) {
				((BaseAuditableEntity) object).setEntityStatus(EntityStatus.ACTIVE);
			}
		}
	}
}
