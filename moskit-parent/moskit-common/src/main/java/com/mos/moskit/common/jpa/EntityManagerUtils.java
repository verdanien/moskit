package com.mos.moskit.common.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.Filter;
import org.hibernate.Session;

import com.mos.moskit.common.proxy.MethodPattern;
import com.mos.moskit.common.proxy.ProxyBuilder;

public final class EntityManagerUtils {
	private static final String CREATE_ENTITY_MANAGER = "createEntityManager";

	public static void sessionFilterDeleted(EntityManager em) {
		Session session = em.unwrap(Session.class);
		Filter filter = session.enableFilter(BaseAuditableEntity.FILTER_NAME);
		filter.setParameter(BaseAuditableEntity.FILTER_PARAM_ACTIVE, EntityStatus.ACTIVE.name());
		filter.setParameter(BaseAuditableEntity.FILTER_PARAM_INACTIVE, EntityStatus.INACTIVE.name());
		filter.setParameter(BaseAuditableEntity.FILTER_PARAM_DELETED, EntityStatus.DELETED.name());
	}

	public static EntityManager sessionFilterClear(EntityManager em) {
		Session session = em.unwrap(Session.class);
		session.disableFilter(BaseAuditableEntity.FILTER_NAME);
		return em;
	}

	public static EntityManagerFactory createSessionFilteredEntityManagerFactory(EntityManagerFactory real) {
		// @formatter:off
		return ProxyBuilder.creatProxyBuilder(EntityManagerFactory.class).withReal(real)
				.withOverridden(MethodPattern.builder().withName("createEntityManager").build(), invocation -> {
					EntityManager result = (EntityManager) invocation.invokeReal();
					sessionFilterDeleted(result);
					return result;
					// return createEntityManager(invocation.getProxy(), result);
				}).build();
		// @formatter:on
	}

	public static EntityManager createEntityManager(EntityManagerFactory entityManagerFactory,
			EntityManager entityManager) {
		// @formatter:off
		return ProxyBuilder.creatProxyBuilder(EntityManager.class).withReal(entityManager)
				.withExceptionHandler((exception, invocation) -> {
					System.out.println("      handled : old " + invocation.getReal().hashCode());
					invocation.setReal(entityManagerFactory.createEntityManager());
					System.out.println("      handled : new " + invocation.getReal().hashCode());
					throw exception;
				}).build();
		// @formatter:on
	}

//	public static EntityManagerFactory createEmfProxy(EntityManagerFactory real) {
//		InvocationHandler handler = new InvocationHandler() {
//			@Override
//			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//				Object invocation = method.invoke(real, args);
//				if (method.getName().contains(CREATE_ENTITY_MANAGER)) {
//					sessionFilterDeleted((EntityManager) invocation);
//				}
//				return invocation;
//			}
//		};
//		return (EntityManagerFactory) Proxy.newProxyInstance(getClassLoader(), new Class<?>[] { EntityManagerFactory.class }, handler);
//	}
//
//	public static EntityManager createEntityManagerProxy(EntityManager entityManager) {
//		InvocationHandler handler = new InvocationHandler() {
//			@Override
//			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//				return null;
//			}
//		};
//		return (EntityManager) Proxy.newProxyInstance(getClassLoader(), new Class<?>[] { EntityManager.class }, handler);
//	}

	private static ClassLoader getClassLoader() {
		return EntityManagerUtils.class.getClassLoader();
	}
}
