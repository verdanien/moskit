package com.mos.moskit.common.jpa;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import com.mos.moskit.common.date.MosDateUtils;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class BaseAuditableEntity extends BaseEntity implements AuditableEntityI<Long> {
	public static final String FILTER_NAME = "entityStatusFilter";
	public static final String FILTER_PARAM_ACTIVE = "entityStatusActive";
	public static final String FILTER_PARAM_INACTIVE = "entityStatusInactive";
	public static final String FILTER_PARAM_DELETED = "entityStatusDeleted";
	public static final String FILTER_ACTIVE = "entity_status = :" + FILTER_PARAM_ACTIVE;
	public static final String FILTER_INACTIVE = "entity_status = :" + FILTER_PARAM_INACTIVE;
	public static final String FILTER_DELETED = "entity_status = :" + FILTER_PARAM_DELETED;
	public static final String FILTER_ENABLED = "entity_status != :" + FILTER_PARAM_DELETED;

	@Column(name = "entity_status", insertable = false, length = 8, nullable = false)
	@Enumerated(EnumType.STRING)
	private @Getter @Setter EntityStatus entityStatus;

	@Column(name = "modify_date")
	private @Getter @Setter LocalDateTime modifyDate;

	@Column(name = "create_date")
	private @Getter @Setter LocalDateTime createDate;

	@Column(name = "remove_date")
	private @Getter @Setter LocalDateTime removeDate;

	@PreRemove
	protected void preRemove() {
		entityStatus = EntityStatus.DELETED;
		removeDate = MosDateUtils.getCurrentDateTime();
	}

	@PreUpdate
	protected void preUpdate() {
		modifyDate = MosDateUtils.getCurrentDateTime();
	}

	@PrePersist
	protected void prePersist() {
		createDate = MosDateUtils.getCurrentDateTime();
		modifyDate = createDate;
	}
}
