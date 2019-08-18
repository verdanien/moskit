//@formatter:off
@AnyMetaDef(name=META_DEF, idType = "long", metaType = "string", metaValues = { 
			@MetaValue(targetEntity = User.class, value = "com.mos.moskit.domain.entity.account.User"), 
			@MetaValue(targetEntity = AbstractRole.class, value = "com.mos.moskit.domain.entity.account.AbstractRole"),
			@MetaValue(targetEntity = UserGroup.class, value = "com.mos.moskit.domain.entity.account.UserGroup") 
		})
//@formatter:on
package com.mos.moskit.domain.entity.permission;

import static com.mos.moskit.domain.entity.permission.Permission.META_DEF;

import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;

import com.mos.moskit.domain.entity.account.AbstractRole;
import com.mos.moskit.domain.entity.account.User;
import com.mos.moskit.domain.entity.account.UserGroup;
