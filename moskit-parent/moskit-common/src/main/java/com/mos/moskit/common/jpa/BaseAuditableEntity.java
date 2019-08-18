package com.mos.moskit.common.jpa;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mos.moskit.common.date.MosDateUtils;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
//@EntityListeners({ AuditingEntityListener.class, BaseAuditableEntityListener.class })
//@EntityListeners({ BaseAuditableEntityListener.class })
public class BaseAuditableEntity extends BaseEntity implements AuditableEntityI<Long> {
	public static final String FILTER_NAME = "entityStatusFilter";
	public static final String FILTER_PARAM_ACTIVE = "entityStatusActive";
	public static final String FILTER_PARAM_INACTIVE = "entityStatusInactive";
	public static final String FILTER_PARAM_DELETED = "entityStatusDeleted";
	public static final String FILTER_ACTIVE = "entity_status = :" + FILTER_PARAM_ACTIVE;
	public static final String FILTER_INACTIVE = "entity_status = :" + FILTER_PARAM_INACTIVE;
	public static final String FILTER_DELETED = "entity_status = :" + FILTER_PARAM_DELETED;
	public static final String FILTER_ENABLED = "entity_status != :" + FILTER_PARAM_DELETED;

	@Column(name = "entity_status", length = 8, nullable = false)
	@Enumerated(EnumType.STRING)
	@JsonIgnore
	private @Getter @Setter EntityStatus entityStatus;

	@Column(name = "modified_date")
	@JsonIgnore
	@LastModifiedDate
	private @Getter @Setter LocalDateTime modifiedDate;

	@Column(name = "created_date")
	@JsonIgnore
	@CreatedDate
	private @Getter @Setter LocalDateTime createdDate;

	@Transient
	public boolean isActive() {
		return EntityStatus.ACTIVE.equals(getEntityStatus());
	}
	
	@PreRemove
	protected void preRemove() {
		entityStatus = EntityStatus.DELETED;
		modifiedDate = MosDateUtils.getCurrentDateTime();
	}

	@PreUpdate
	protected void preUpdate() {
		if (entityStatus == null) {
			entityStatus = EntityStatus.ACTIVE;
		}
		modifiedDate = MosDateUtils.getCurrentDateTime();
	}

	@PrePersist
	protected void prePersist() {
		if (entityStatus == null) {
			entityStatus = EntityStatus.ACTIVE;
		}
		createdDate = MosDateUtils.getCurrentDateTime();
		modifiedDate = createdDate;
	}
}
