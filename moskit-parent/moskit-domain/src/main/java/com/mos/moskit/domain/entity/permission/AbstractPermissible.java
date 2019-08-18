package com.mos.moskit.domain.entity.permission;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Any;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mos.moskit.common.jpa.BaseAuditableEntity;
import com.mos.moskit.domain.converter.ClassConverter;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class AbstractPermissible extends BaseAuditableEntity {

//	@OneToMany
//	@JoinTable(name = "permission", inverseJoinColumns = {@JoinColumn(referencedColumnName="entity_id"),@JoinColumn(referencedColumnName="entity_class")}, joinColumns = @JoinColumn(referencedColumnName = "id"))
//	@JoinTable(name = "permission", inverseJoinColumns = {@JoinColumn(referencedColumnName="entity_id"),@JoinColumn(referencedColumnName="entity_class")}, joinColumns = @JoinColumn(referencedColumnName = "id"))
//	@OneToMany(targetEntity = Permission.class, mappedBy = "entity_id")
//	@JoinColumn(name = "relation_id")
//	@Cascade({ org.hibernate.annotations.CascadeType.ALL })
//	@Any(metaDef = CONTACT_TYPE_META_DEF, fetch = FetchType.EAGER, metaColumn = @Column(name = "relation_type", insertable = false, updatable = false, nullable = false), optional = false)
//	private @Getter @Setter List<Permission> permissions = new ArrayList<>();

	//@formatter:off
//	@ManyToAny(metaDef = Permission.META_DEF, metaColumn = @Column(name = Permission.META_DEF))
//	@JoinTable(name = "permission", 
//		joinColumns = @JoinColumn(referencedColumnName = "id"), 
//		inverseJoinColumns= @JoinColumn(referencedColumnName = "entity_id")
//	)
	//@formatter:on

//	@OneToMany
//	@OneToMany(mappedBy = "permissible", targetEntity = Permission.class, orphanRemoval = true)
//	@OneToMany
//	@JoinTable(name = "permission",
//		inverseJoinColumns = {
//				@JoinColumn(name=Permission.META_COL,referencedColumnName=Permission.META_COL),
//				@JoinColumn(name=Permission.META_COL_ID,referencedColumnName=Permission.META_COL_ID)
//		},
//		joinColumns = {
//				@JoinColumn(name="relationType", referencedColumnName="relation_type"),
//				@JoinColumn(name=Permission.META_COL_ID,referencedColumnName="id")
//		}
//	)

	@JsonManagedReference
	@ManyToAny(metaDef = Permission.META_DEF, metaColumn = @Column(name = Permission.META_COL))
	@JoinTable(name = "permission", joinColumns = @JoinColumn(name = "entity_id"), inverseJoinColumns = @JoinColumn(name = "id"))
	private @Getter @Setter List<Permission> permissions = new ArrayList<>();

}
