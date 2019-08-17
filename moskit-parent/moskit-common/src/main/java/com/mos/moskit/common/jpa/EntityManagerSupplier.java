package com.mos.moskit.common.jpa;

import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import lombok.Getter;
import lombok.Setter;

public class EntityManagerSupplier {
	private EntityManager entityManager;

	private @Getter @Setter Consumer<EntityManager> onEntityManagerChange;

	protected EntityManagerSupplier() {
		this(null);
	}

	public EntityManagerSupplier(EntityManager entityManger) {
		setEntityManager(entityManger);
	}

	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
		updateEntityManager();
	}

	private void updateEntityManager() {
		if (onEntityManagerChange != null && entityManager != null) {
			onEntityManagerChange.accept(entityManager);
		}
	}

	public EntityManager getEntityManager() {
		updateEntityManager();
		return entityManager;
	}

	public static class ExtendedEntityManagerSupplier extends EntityManagerSupplier {

		@Override
		@PersistenceContext(type = PersistenceContextType.EXTENDED)
		protected void setEntityManager(EntityManager entityManger) {
			super.setEntityManager(entityManger);
		}

	}

//	@DependsOn("entityManager")
//	@Transactional
	public static class TransactionalEntityManagerSupplier extends EntityManagerSupplier {

		@Override
		@PersistenceContext(type = PersistenceContextType.TRANSACTION)
		protected void setEntityManager(EntityManager entityManger) {
			super.setEntityManager(entityManger);
		}

	}

}
