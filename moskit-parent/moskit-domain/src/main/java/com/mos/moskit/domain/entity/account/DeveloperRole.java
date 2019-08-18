package com.mos.moskit.domain.entity.account;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(Role.Values.DEVELOPER)
public class DeveloperRole extends AbstractRole {

}
