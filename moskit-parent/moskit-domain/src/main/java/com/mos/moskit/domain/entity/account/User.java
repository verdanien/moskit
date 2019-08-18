package com.mos.moskit.domain.entity.account;

import java.security.acl.Permission;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mos.moskit.common.jpa.EntityUtils;
import com.mos.moskit.domain.entity.permission.AbstractPermissible;

import lombok.Getter;
import lombok.Setter;

@Entity
@SequenceGenerator(name = EntityUtils.SEQ_GEN, sequenceName = "seq_user_id", allocationSize = 1, initialValue = 1)
public class User extends AbstractPermissible implements UserDetails {

	@Column(unique = true, length = 50)
	@NotNull
	private @Getter @Setter String login;

	@Column
	@NotEmpty
	@JsonIgnore
	private @Getter @Setter String password;

//	@Column
//	@NotNull
//	@JsonIgnore
//	private @Getter @Setter Integer salt;

	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, mappedBy = "user", orphanRemoval = false)
	private @Getter @Setter List<AbstractRole> roles;

	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, mappedBy = "user", orphanRemoval = false)
	private @Getter @Setter List<UserGroupMember> userGroupMembers;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> totalPermissions = new HashSet<>();
		totalPermissions.addAll(getPermissions());
		for (AbstractRole role : getRoles()) {
			totalPermissions.add(role);
			if (role.isActive()) {
				totalPermissions.addAll(role.getPermissions());
			}
		}
		for (UserGroupMember member : getUserGroupMembers()) {
			if (member.isActive()) {
				totalPermissions.addAll(member.getUserGroup().getPermissions());
			}
		}
		return totalPermissions;
	}

	@Override
	public String getUsername() {
		return getLogin();
	}

	@Override
	public boolean isAccountNonExpired() {
		return isActive();
	}

	@Override
	public boolean isAccountNonLocked() {
		return isActive();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isActive();
	}

	@Override
	public boolean isEnabled() {
		return isActive();
	}
}
