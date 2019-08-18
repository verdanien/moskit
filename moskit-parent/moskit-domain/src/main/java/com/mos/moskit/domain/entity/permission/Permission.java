package com.mos.moskit.domain.entity.permission;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Any;
import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mos.moskit.common.jpa.BaseAuditableEntity;
import com.mos.moskit.common.jpa.EntityUtils;
import com.mos.moskit.domain.converter.ClassConverter;

import lombok.Getter;
import lombok.Setter;

@Entity
@SequenceGenerator(name = EntityUtils.SEQ_GEN, sequenceName = "seq_permission", initialValue = 1, allocationSize = 1)
public class Permission extends BaseAuditableEntity implements GrantedAuthority {
	private static final long serialVersionUID = 1L;
	public static final String META_DEF = "PERMISSIBLE_META_DEF";
	public static final String META_COL = "entity_class";
	public static final String META_COL_ID = "entity_id";

	@Column
	@NotNull
	private @Getter @Setter String name;

	@JsonBackReference
	@Any(metaDef = META_DEF, metaColumn = @Column(name = META_COL, insertable = false, updatable = false, nullable = false), optional = false)
	@JoinColumn(name = META_COL_ID)
//	//@formatter:off
//	@AnyMetaDef(name=META_DEF, idType = "long", metaType = "string", metaValues = { 
//			@MetaValue(targetEntity = User.class, value = "com.mos.moskit.domain.entity.account.User"), 
//			@MetaValue(targetEntity = AbstractRole.class, value = "com.mos.moskit.domain.entity.account.AbstractRole"),
//			@MetaValue(targetEntity = UserGroup.class, value = "com.mos.moskit.domain.entity.account.UserGroup") 
//			})
//	//@formatter:on
	private @Getter @Setter AbstractPermissible permissible;

	@Convert(converter = ClassConverter.class)
	@Column(name = META_COL, updatable = false, insertable = false, nullable = false)
	private @Getter @Setter Class<?> relationType;

	@Transient
	@Override
	public String getAuthority() {
		return name;
	}
}
