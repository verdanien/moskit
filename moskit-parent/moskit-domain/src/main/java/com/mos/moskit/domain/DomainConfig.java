package com.mos.moskit.domain;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.sql.DataSource;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mos.moskit.common.jpa.EntityManagerSupplier;
import com.mos.moskit.common.jpa.EntityManagerSupplier.TransactionalEntityManagerSupplier;
import com.mos.moskit.common.jpa.EntityManagerUtils;

import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
@ComponentScan
@EnableTransactionManagement
@EnableJpaRepositories
@EnableJpaAuditing
public class DomainConfig {

	private static final String LIQUIBASE_PROFILE = "dev";
	private static final String LIQUIBASE_CHANGELOG_MASTER = "classpath:/db/changelog/changesets-master.xml";
	private static final String BEAN_LIQUIBASE = "liquibase";

	@Autowired
	private ApplicationContext appContext;

	@PostConstruct
	public void initialized() {
		log.info("Domain Config initialized");
	}

	@Bean(name = BEAN_LIQUIBASE)
	public SpringLiquibase liquibase(DataSource dataSource) {
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setDataSource(dataSource);
		liquibase.setChangeLog(LIQUIBASE_CHANGELOG_MASTER);
		liquibase.setContexts(LIQUIBASE_PROFILE);
		liquibase.setDropFirst(false);
		liquibase.setShouldRun(true);
		return liquibase;
	}

	@Bean
	@DependsOn(BEAN_LIQUIBASE)
	public EntityManagerFactory entityManagerFactory(DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);

		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("com.mos.moskit.domain.entity");
		factory.setDataSource(dataSource);
		factory.setJpaProperties(createHibernateProperties());
//		factory.getJpaPropertyMap().put(EntityManagerFactoryBuilderImpl.INTEGRATOR_PROVIDER, integr);

		factory.afterPropertiesSet();

		EntityManagerFactory entityManagerFactory = factory.getObject();
		return entityManagerFactory;
	}

	private Properties createHibernateProperties() {
		Properties properties = new Properties();
		/* standard hbn */
		properties.setProperty(AvailableSettings.HBM2DDL_AUTO, "validate");
//		properties.setProperty(AvailableSettings.HBM2DDL_AUTO, "create-drop");
		properties.setProperty(AvailableSettings.MERGE_ENTITY_COPY_OBSERVER, "allow");
		properties.setProperty(AvailableSettings.ENABLE_LAZY_LOAD_NO_TRANS, "true");
		properties.setProperty(AvailableSettings.ORDER_INSERTS, "true");
		properties.setProperty(AvailableSettings.ORDER_UPDATES, "true");
		properties.setProperty(AvailableSettings.SHOW_SQL, "true");
		properties.setProperty(AvailableSettings.GENERATE_STATISTICS, "false");
		properties.setProperty(AvailableSettings.PHYSICAL_NAMING_STRATEGY, "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
		properties.setProperty(AvailableSettings.IMPLICIT_NAMING_STRATEGY, "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");

		/* second level cache */
//		properties.setProperty(AvailableSettings.USE_SECOND_LEVEL_CACHE, "true");
//		properties.setProperty(AvailableSettings.USE_QUERY_CACHE, "true");
//		properties.setProperty(AvailableSettings.CACHE_REGION_FACTORY, "org.hibernate.cache.ehcache.EhCacheRegionFactory");
		// properties.setProperty("cache.provider_class",
		// "org.hibernate.cache.EhCacheProvider");

		/* connection */
//		properties.setProperty(AvailableSettings.C3P0_MIN_SIZE, "1");
//		properties.setProperty(AvailableSettings.C3P0_MAX_SIZE, "200");
//		properties.setProperty(AvailableSettings.C3P0_TIMEOUT, "60"); // in seconds
//		properties.setProperty(AvailableSettings.C3P0_MAX_STATEMENTS, "50");
//		properties.setProperty(AvailableSettings.C3P0_IDLE_TEST_PERIOD, "3000");
		return properties;
	}

	@Bean
	@DependsOn(BEAN_LIQUIBASE)
	public EntityManagerSupplier entityManagerSupplier() {
		EntityManagerSupplier entityManagerSupplier = new TransactionalEntityManagerSupplier();
		appContext.getAutowireCapableBeanFactory().autowireBean(entityManagerSupplier);
		entityManagerSupplier.setOnEntityManagerChange(em -> {
//			EntityManagerUtils.sessionFilterDeleted(em);
		});
		return entityManagerSupplier;
	}

	@Bean
	public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.setFlushMode(FlushModeType.AUTO);
		return entityManager;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager txManager = new JpaTransactionManager(entityManagerFactory);
		txManager.setRollbackOnCommitFailure(true);
		return txManager;
	}
}
