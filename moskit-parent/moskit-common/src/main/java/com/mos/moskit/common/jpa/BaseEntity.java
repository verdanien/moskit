package com.mos.moskit.common.jpa;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class BaseEntity extends AbstractEntity<Long> {

	@Id
	@GeneratedValue(generator = EntityUtils.SEQ_GEN)
	private @Getter @Setter Long id;

}
