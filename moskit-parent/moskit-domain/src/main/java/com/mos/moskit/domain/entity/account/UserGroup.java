package com.mos.moskit.domain.entity.account;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mos.moskit.common.jpa.EntityUtils;
import com.mos.moskit.domain.entity.permission.AbstractPermissible;

import lombok.Getter;
import lombok.Setter;

@Entity
@SequenceGenerator(name = EntityUtils.SEQ_GEN, sequenceName = "seq_user_group", initialValue = 1, allocationSize = 1)
public class UserGroup extends AbstractPermissible {
	@Column(unique = true)
	@NotNull
	private @Getter @Setter String name;

	@JsonManagedReference
	@OneToMany(mappedBy = "userGroup")
	private @Getter @Setter List<UserGroupMember> members;
}
