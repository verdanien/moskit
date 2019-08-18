package com.mos.moskit.domain.entity.account;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mos.moskit.common.jpa.BaseAuditableEntity;
import com.mos.moskit.common.jpa.EntityUtils;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "user_group_id" }) })
@SequenceGenerator(name = EntityUtils.SEQ_GEN, sequenceName = "seq_user_group_member", initialValue = 1, allocationSize = 1)
public class UserGroupMember extends BaseAuditableEntity {

	@JsonBackReference
	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id")
	@NotNull
	private @Getter @Setter User user;

	@JsonBackReference
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "user_group_id")
	private @Getter @Setter UserGroup userGroup;

	@Column
	@NotNull
	private @Getter @Setter LocalDate startDate;

	@Column
	private @Getter @Setter LocalDate endDate;

}
