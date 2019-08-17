package com.mos.moskit.common.jpa;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface AuditableEntityI<ID extends Serializable> {

	EntityStatus getEntityStatus();

	void setEntityStatus(EntityStatus entityStatus);

	LocalDateTime getModifiedDate();

	void setModifiedDate(LocalDateTime modifyDate);

	LocalDateTime getCreatedDate();

	void setCreatedDate(LocalDateTime createDate);
}
