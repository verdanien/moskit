package com.mos.moskit.domain.entity.account;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mos.moskit.common.jpa.BaseAuditableEntity;
import com.mos.moskit.common.jpa.EntityUtils;
import com.mos.moskit.domain.entity.permission.AbstractPermissible;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "role", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "discriminator" }) })
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@SequenceGenerator(name = EntityUtils.SEQ_GEN, sequenceName = "seq_role", initialValue = 1, allocationSize = 1)
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING, length = 20, columnDefinition = "varchar(20)")
public abstract class AbstractRole extends AbstractPermissible implements GrantedAuthority{

	@Column(nullable = false, insertable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private @Getter @Setter Role discriminator;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.DETACH)
	@JoinColumn(name = "user_id")
	private @Getter @Setter User user;
	
	@Override
	public String getAuthority() {
		return "ROLE_"+discriminator.name();
	}
}
