package com.mos.moskit.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.mos.moskit.common.jpa.BaseAuditableEntity;
import com.mos.moskit.common.jpa.EntityUtils;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "database_connection")
@SequenceGenerator(name = EntityUtils.SEQ_GEN, sequenceName = "seq_database_connection", initialValue = 1, allocationSize = 1)
public class DatabaseConnection extends BaseAuditableEntity {

	@Column(length = 100, unique = true)
	@NotEmpty
	@Length(max = 100)
	public @Getter @Setter String name;

	@Column
	@NotNull
	@Min(value = 0)
	@Max(value = 99999)
	public @Getter @Setter Integer port;

	@Column
	@NotEmpty
	public @Getter @Setter String host;

	@Column
	@NotEmpty
	public @Getter @Setter String user;

	@Column
	public @Getter @Setter String password;

}
