package com.mos.moskit.domain.entity.account;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(Role.Values.ADMIN)
public class AdminRole extends AbstractRole {

}
