package com.github.firelore.config;

import java.util.Properties;

import javax.naming.NamingException;
import javax.persistence.SharedCacheMode;
import javax.sql.DataSource;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.config.TargetServer;
import org.eclipse.persistence.logging.JavaLog;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.instrument.classloading.ReflectiveLoadTimeWeaver;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.transaction.jta.JtaTransactionManager;

@Configuration
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@EnableJpaRepositories(basePackages="com.github.firelore.repository")
public class DataConfig implements TransactionManagementConfigurer {
	@Bean
	public Properties jpaProperties(){
		Properties jpaProperties = new Properties();
		jpaProperties.put("javax.persistence.sharedCache.mode", SharedCacheMode.ENABLE_SELECTIVE);
		jpaProperties.put(PersistenceUnitProperties.TRANSACTION_TYPE, "JTA");
		jpaProperties.put(PersistenceUnitProperties.TARGET_SERVER, TargetServer.DEFAULT);
		jpaProperties.put(PersistenceUnitProperties.ID_VALIDATION, "NULL");
		jpaProperties.put(PersistenceUnitProperties.LOGGING_LOGGER, JavaLog.class.getName());
		jpaProperties.put(PersistenceUnitProperties.LOGGING_LEVEL, JavaLog.WARNING_LABEL);
		jpaProperties.put(PersistenceUnitProperties.CATEGORY_LOGGING_LEVEL_ + JavaLog.CONNECTION, JavaLog.CONFIG_LABEL);
		jpaProperties.put(PersistenceUnitProperties.CATEGORY_LOGGING_LEVEL_ + JavaLog.SQL, JavaLog.FINEST_LABEL);
		jpaProperties.put("javax.persistence.schema-generation.database.action", "drop-and-create");
		jpaProperties.put("javax.persistence.schema-generation.scripts.create-target", "create.ddl");
		jpaProperties.put("javax.persistence.sql-load-script-source", "insert.sql");
		
		return jpaProperties;
	}
	
	@Bean
	public LoadTimeWeaver loadTimeWeaver() {
		return new ReflectiveLoadTimeWeaver();
	}
	
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		EclipseLinkJpaVendorAdapter adapter = new EclipseLinkJpaVendorAdapter();
		adapter.setShowSql(true);
		adapter.setDatabase(Database.H2);
		adapter.setDatabasePlatform("org.eclipse.persistence.platform.database.H2Platform");
		return adapter;
	}
	
	@Bean
	public JtaTransactionManager transactionManager() {
		return new JtaTransactionManager();
	}
	
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return transactionManager();
	}
	
	@Bean
	LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException{
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setPackagesToScan("com.github.firelore.domain");
		emf.setPersistenceUnitName("transactionUnit");
		emf.setJtaDataSource(datasource());
		emf.setJpaVendorAdapter(jpaVendorAdapter());
		emf.setLoadTimeWeaver(loadTimeWeaver());
		emf.setJpaProperties(jpaProperties());

		return emf;
	}

	@Bean
	public DataSource datasource() throws NamingException {
		DataSource ds = new JndiTemplate().lookup("java:/jdbc/transaction-ds", DataSource.class); 
		return ds;
	}
}
