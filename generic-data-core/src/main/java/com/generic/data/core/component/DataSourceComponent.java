package com.generic.data.core.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.generic.data.core.common.DataSourceContextHolder;
import com.generic.data.core.common.GenericDataException;
import com.generic.data.core.config.AppConfig;
import com.generic.data.core.config.DbPropertySetting;
import com.generic.data.core.dto.DbProperty;

/**
 * The {@link DataSourceComponent} class to handle create/refresh/re-connect
 * DataSource connection.
 * 
 * @author praveen.kumar
 *
 */
@Component
public class DataSourceComponent implements BeanFactoryAware {

	private static final String DATASOURCE_NOT_AVAILABLE_EITHER_NO_DETAILS_AVAILABLE_IN_PROPERTY_FILE_WRONG_CONFIGURATION_IN_PROPERTY_FILE = "Datasource not available ,Either no details available in property file /wrong configuration  in property file.";
	private static final Logger LOGGER = LogManager.getLogger(DataSourceComponent.class);
	private static final String GENERIC_DATA = "generic-data";

	@Autowired
	private AppConfig dbConfig;

	private ConfigurableBeanFactory beanFactory;
	private Set<String> availableDataSources = new HashSet<>();

	@PostConstruct
	private void loadSessionFactory() throws GenericDataException {
		checkIfValidConfiguration();
		loadAllSessionFactory(dbConfig);
	}

	/**
	 * Method to handle refresh all DataSource object as such any change in property
	 * details
	 * 
	 * @param config
	 * @throws GenericDataException
	 */
	public void refreshDatasource(AppConfig config) throws GenericDataException {
		checkIfValidConfiguration();
		// remove active DataSource from context
		availableDataSources.forEach(this::removeActiveDataSourceFromContext);
		// load DataSource into context.
		loadAllSessionFactory(config);
	}

	/**
	 * 
	 * @param config
	 * @throws GenericDataException
	 */
	private void loadAllSessionFactory(AppConfig config) throws GenericDataException {
		availableDataSources.clear();
		try {
			config.getConfig().forEach(this::forDbConnection);
		} catch (Exception e) {
			availableDataSources.clear();
			LOGGER.error(e);
			throw new GenericDataException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param dbProperty
	 */
	private void forDbConnection(DbProperty dbProperty) {
		try {
			registerDbConnection(dbProperty);
		} catch (Exception ex) {
			LOGGER.error(String.format("DataSource [%s] not available for use reason [%s]",
					dbProperty.getDataSourceName(), ex.getMessage()));
		}
	}

	/**
	 * 
	 * @param dbProperty
	 * @throws GenericDataException
	 */
	public void addDataSource(DbProperty dbProperty) throws GenericDataException {
		addDataSource(dbProperty);
		// add DB property in AppConfig.
		if (dbConfig.getConfig() == null) {
			dbConfig.setConfig(new ArrayList<>());
		}
		dbConfig.getConfig().add(dbProperty);
	}

	public void registerDbConnection(DbProperty dbProperty) throws GenericDataException {
		// Creating SessionFactory and register with bean Factory
		registerSessionFactory(createSessionFactory(dbProperty), dbProperty);
		// add into available DataSource list
		availableDataSources.add(dbProperty.getDataSourceName());
	}

	/**
	 * Method to handle update DataSource object.
	 * 
	 * @param dbProperty
	 * @throws GenericDataException
	 */
	public void updateDataSource(DbProperty dbProperty) throws GenericDataException {
		// remove active DataSource from context
		removeActiveDataSourceFromContext(dbProperty.getDataSourceName());
		// register DataSource into context.
		registerDbConnection(dbProperty);
		// update AppConfig
		Predicate<DbProperty> isMatchingDataSource = item -> item.getDataSourceName()
				.equalsIgnoreCase(dbProperty.getDataSourceName());
		// find and remove matching object
		dbConfig.getConfig().stream().filter(isMatchingDataSource)
				.forEach(item -> dbConfig.getConfig().remove(dbProperty));
		// add updated object
		dbConfig.getConfig().add(dbProperty);

	}

	/**
	 * Method to handle update DataSource object.
	 * 
	 * @param dbProperty
	 * @throws GenericDataException
	 */
	public void deleteDataSource(String dataSourceName) {
		// remove active DataSource from context
		removeActiveDataSourceFromContext(dataSourceName);
		// update AppConfig
		Predicate<DbProperty> isMatchingDataSource = item -> item.getDataSourceName()
				.equalsIgnoreCase(dataSourceName);
		// find and remove matching object
		dbConfig.getConfig().stream().filter(isMatchingDataSource)
				.forEach(item -> dbConfig.getConfig().remove(item));
	}

	/**
	 * 
	 * @param dbProperty
	 * @return SessionFactory
	 * @throws GenericDataException
	 */
	private SessionFactory createSessionFactory(DbProperty dbProperty) throws GenericDataException {
		// Creating StandardServiceRegistryBuilder
		SessionFactory sessionFactory = null;
		StandardServiceRegistry standardServiceRegistry = null;
		try {
			StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
			// HiberNate settings
			Map<String, Object> dbSettings = new HashMap<>();
			dbSettings.put(DbPropertySetting.URL, dbProperty.getUrl());
			dbSettings.put(DbPropertySetting.USER, dbProperty.getUserName());
			dbSettings.put(DbPropertySetting.PASS, dbProperty.getPassWord());
			dbSettings.put(DbPropertySetting.DRIVER, dbProperty.getDriverClassName());
			dbSettings.put(DbPropertySetting.DIALECT, dbProperty.getDialect());
			dbSettings.put(DbPropertySetting.CURRENT_SESSION_CONTEXT_CLASS, DbPropertySetting.THREAD);
			dbSettings.put(DbPropertySetting.SHOW_SQL, DbPropertySetting.TRUE);
			dbSettings.put(DbPropertySetting.FORMAT_SQL, DbPropertySetting.TRUE);
			dbSettings.put(DbPropertySetting.CONNECTION_PROVIDER, DbPropertySetting.HIKARI_CONNECTION_PROVIDER);
			dbSettings.put(DbPropertySetting.HIKARI_CONNECTION_TIME_OUT, dbProperty.getTimeOut());
			dbSettings.put(DbPropertySetting.HIKARI_MAX_POOL_SIZE, dbProperty.getMaxSize());
			dbSettings.put(DbPropertySetting.HIKARI_MAX_LIFE_TIME, dbProperty.getMaxLifeTime());
			dbSettings.put(DbPropertySetting.HIKARI_POOL_NAME, dbProperty.getDataSourceName());
			dbSettings.put(DbPropertySetting.HIKARI_ALLOW_POOL_SUSPENSION,
					String.valueOf(dbProperty.isAllowFailover()));
			dbSettings.put(DbPropertySetting.HIKARI_ONLY_FOR_READ, String.valueOf(dbProperty.isReadOnly()));
			if (!StringUtils.isEmpty(dbProperty.getSchemaName()))
				dbSettings.put(AvailableSettings.DEFAULT_SCHEMA, dbProperty.getSchemaName());
			// Apply database settings
			registryBuilder.applySettings(dbSettings);
			// Creating registry
			standardServiceRegistry = registryBuilder.build();
			// Creating MetadataSources
			MetadataSources sources = new MetadataSources(standardServiceRegistry);
			// Creating MetaData
			Metadata metadata = sources.getMetadataBuilder().build();
			sessionFactory = metadata.getSessionFactoryBuilder().build();
		} catch (Exception e) {
			LOGGER.error(e);
			if (standardServiceRegistry != null) {
				StandardServiceRegistryBuilder.destroy(standardServiceRegistry);
			}
			throw new GenericDataException(
					String.format("Exception[%s] occured during datasource creation", e.getMessage()));
		}
		return sessionFactory;
	}

	/**
	 * Method to add sessionFactory object into spring context
	 */
	private void registerSessionFactory(SessionFactory sessionFactory, DbProperty dbProperty) {
		beanFactory.registerSingleton(GENERIC_DATA.concat(dbProperty.getDataSourceName()), sessionFactory);
		LOGGER.info("Datasource[{}] connected successfully with database property[{}] and available for use.",
				dbProperty.getDataSourceName(), dbProperty);
	}

	/**
	 * Method to remove active DataSource object from bean context.
	 */
	private void removeActiveDataSourceFromContext(String dataSourceName) {
		BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
		// check if DataSource available than remove from bean context
		removeBeanWhenContains(dataSourceName, registry);
		// remove from available DataSource set so that no chance of duplicate instance
		// of same DataSource.
		availableDataSources.remove(dataSourceName);
		LOGGER.debug("Datasource[{}] has been successfully placed in  refresh context", dataSourceName);

	}

	/**
	 * 
	 * @param beanName
	 * @param registry
	 * @return
	 */
	private void removeBeanWhenContains(String beanName, BeanDefinitionRegistry registry) {
		// append String with generic-data
		String lookupDataSourceName = GENERIC_DATA.concat(beanName);
		if (beanFactory.containsBean(lookupDataSourceName)) {
			// yes available
			// destroy old connection if open
			SessionFactory sessionFactory = lookForSessionFactory(beanName);
			if (sessionFactory.isOpen()) {
				sessionFactory.close();
			}
			// kill active service registry
			if (sessionFactory.getSessionFactoryOptions().getServiceRegistry() != null) {
				StandardServiceRegistryBuilder.destroy(sessionFactory.getSessionFactoryOptions().getServiceRegistry());
			}
			// remove from BeanContext.
			registry.removeBeanDefinition(lookupDataSourceName);
		}
	}

	/**
	 * Method to get session object from bean factory
	 * 
	 * @return
	 * @throws GenericDataException
	 */
	public Session getSession() throws GenericDataException {
		String dataSourceName = DataSourceContextHolder.getDataSourceContext();
		if (StringUtils.isEmpty(dataSourceName))
			throw new GenericDataException("DataSource Config not found in thread context");
		try {
			// look for DataSource respective sessionFactory and get session for query.
			return lookForSessionFactory(dataSourceName).getCurrentSession();
		} catch (BeansException e) {
			LOGGER.error(e);
			throw new GenericDataException(String.format(
					"datasource %s not found in beanfactory,possibility either wrong datasourcename/datasource %s not created yet.Transaction terminated",
					dataSourceName, dataSourceName));
		} catch (Exception ex) {
			LOGGER.error(ex);
		}
		throw new GenericDataException(String.format("Connection not available for datasource %s ", dataSourceName));
	}

	private void checkIfValidConfiguration() throws GenericDataException {
		if (CollectionUtils.isEmpty(dbConfig.getConfig())) {
			LOGGER.error(
					DATASOURCE_NOT_AVAILABLE_EITHER_NO_DETAILS_AVAILABLE_IN_PROPERTY_FILE_WRONG_CONFIGURATION_IN_PROPERTY_FILE);
			throw new GenericDataException(
					DATASOURCE_NOT_AVAILABLE_EITHER_NO_DETAILS_AVAILABLE_IN_PROPERTY_FILE_WRONG_CONFIGURATION_IN_PROPERTY_FILE);
		}
		if (isAnyDuplicateDataSourceNameInConfig()) {
			LOGGER.error(
					"Wrong Configuration/Duplicate Entry found -All active connection terminated if any ,which means  datasource not available for use.");
			throw new GenericDataException(
					"Wrong Configuration/Duplicate Entry found -All active connection terminated if any, which means  datasource not available for use.");
		}
		isMandatoryFieldMissing();
	}

	private boolean isMandatoryFieldMissing() {
		// check if mandatory field
		// (URL,userName,password,driverClass,dialect,dataSourceName) is
		// missing
		dbConfig.getConfig().forEach(property -> {
			if (StringUtils.isEmpty(property.getUrl()) || StringUtils.isEmpty(property.getPassWord())
					|| StringUtils.isEmpty(property.getDriverClassName()) || StringUtils.isEmpty(property.getUserName())
					|| StringUtils.isEmpty(property.getDialect())
					|| StringUtils.isEmpty(property.getDataSourceName())) {
				LOGGER.error("Mandatory field missing in configuration[{}]", property);
				throw new IllegalArgumentException(
						String.format("Mandatory field missing in configuration[ %s ]", property.toString()));
			}
		});
		return false;

	}

	/**
	 * 
	 * @param dataSourceName
	 * @return
	 */
	private SessionFactory lookForSessionFactory(String dataSourceName) {
		return (SessionFactory) beanFactory.getBean(GENERIC_DATA.concat(dataSourceName));

	}

	private boolean isAnyDuplicateDataSourceNameInConfig() {
		return !CollectionUtils.isEmpty(getDuplicates(this.getDbConfig().getConfig()));
	}

	/**
	 * 
	 * @param dbConfigList
	 * @return
	 */
	private static List<DbProperty> getDuplicates(List<DbProperty> dbConfigList) {
		return getDuplicatesMap(dbConfigList).values().stream().filter(duplicates -> duplicates.size() > 1)
				.flatMap(Collection::stream).collect(Collectors.toList());
	}

	/**
	 * 
	 * @param dbConfigList
	 * @return
	 */
	private static Map<String, List<DbProperty>> getDuplicatesMap(List<DbProperty> dbConfigList) {
		return dbConfigList.stream().collect(Collectors.groupingBy(DbProperty::uniqueAttributes));
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = (ConfigurableBeanFactory) beanFactory;
	}

	public AppConfig getDbConfig() {
		return dbConfig;
	}

	public void setDbConfig(AppConfig dbConfig) {
		this.dbConfig = dbConfig;
	}

	public Set<String> getAvailableDataSources() {
		return availableDataSources;
	}

	public void setAvailableDataSources(Set<String> availableDataSources) {
		this.availableDataSources = availableDataSources;
	}

}
