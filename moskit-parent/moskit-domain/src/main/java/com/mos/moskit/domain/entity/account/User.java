package com.mos.moskit.domain.entity.account;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.mos.moskit.common.jpa.BaseAuditableEntity;
import com.mos.moskit.common.jpa.EntityUtils;

import lombok.Getter;
import lombok.Setter;

@Entity
@SequenceGenerator(name = EntityUtils.SEQ_GEN, sequenceName = "seq_user", allocationSize = 1, initialValue = 1)
public class User extends BaseAuditableEntity {

	private @Getter @Setter String login;
	private @Getter @Setter String password;
}
