package com.generic.data.core.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.generic.data.core.common.DataSourceContextHolder;
import com.generic.data.core.common.GenericDataException;
import com.generic.data.core.component.DataSourceComponent;
import com.generic.data.core.dto.DbProperty;
import com.generic.data.core.generator.SequenceGenerator;

/**
 * @author praveen.kumar This class serves as the Base class for all other DAOs
 *         - namely to hold common CRUD methods.
 * 
 * 
 * 
 */

@Repository
public class GenericDaoImpl implements GenericDao {

	private static final String EXCEPTION_S_OCCURED_DURING_QUERY_EXECUTION = "Exception[%s] occured during query execution ";
	@Autowired
	private DataSourceComponent dataSourceComponent;
	@Autowired
	private SequenceGenerator sequenceGenerator;

	public GenericDaoImpl() {
		// Doing nothing
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Integer countBySQLQuery(String sqlQueryString, Map<String, Object> map) throws GenericDataException {
		try (Session session = dataSourceComponent.getSession()) {
			session.beginTransaction();
			if (!CollectionUtils.isEmpty(map))
				return (Integer) session.createNativeQuery(sqlQueryString).setProperties(map).uniqueResult();
			else
				return (Integer) session.createNativeQuery(sqlQueryString).uniqueResult();
		} catch (Exception ex) {
			throw new GenericDataException(String.format(EXCEPTION_S_OCCURED_DURING_QUERY_EXECUTION, ex));
		} finally {
			// clear DataSourceContext resources/cache so that it won't impact memory.
			DataSourceContextHolder.clearDataSourceContext();
		}
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Object findBySQLQuery(String sqlQueryString, Map<String, Object> map) throws GenericDataException {
		try (Session session = dataSourceComponent.getSession()) {
			session.beginTransaction();
			if (!CollectionUtils.isEmpty(map))
				return session.createNativeQuery(sqlQueryString).setProperties(map).list();
			else
				return session.createNativeQuery(sqlQueryString).list();
		} catch (Exception ex) {
			throw new GenericDataException(String.format(EXCEPTION_S_OCCURED_DURING_QUERY_EXECUTION, ex));
		} finally {
			// clear DataSourceContext resources/cache so that it won't impact memory.
			DataSourceContextHolder.clearDataSourceContext();
		}
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Integer insertBySQLQuery(String sqlQueryString, Map<String, Object> map) throws GenericDataException {
		try (Session session = dataSourceComponent.getSession()) {
			session.beginTransaction();
			if (!CollectionUtils.isEmpty(map))
				return session.createNativeQuery(sqlQueryString).setProperties(map).executeUpdate();
			else
				return session.createNativeQuery(sqlQueryString).executeUpdate();
		} catch (Exception ex) {
			throw new GenericDataException(String.format(EXCEPTION_S_OCCURED_DURING_QUERY_EXECUTION, ex));
		} finally {
			// clear DataSourceContext resources/cache so that it won't impact memory.
			DataSourceContextHolder.clearDataSourceContext();
		}
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Integer updateBySQLQuery(String sqlQueryString, Map<String, Object> map) throws GenericDataException {
		try (Session session = dataSourceComponent.getSession()) {
			session.beginTransaction();
			if (!CollectionUtils.isEmpty(map))
				return session.createNativeQuery(sqlQueryString).setProperties(map).executeUpdate();
			else
				return session.createNativeQuery(sqlQueryString).executeUpdate();
		} catch (Exception ex) {
			throw new GenericDataException(String.format(EXCEPTION_S_OCCURED_DURING_QUERY_EXECUTION, ex));
		} finally {
			// clear DataSourceContext resources/cache so that it won't impact memory.
			DataSourceContextHolder.clearDataSourceContext();
		}
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Integer deleteBySQLQuery(String sqlQueryString, Map<String, Object> map) throws GenericDataException {
		try (Session session = dataSourceComponent.getSession()) {
			session.beginTransaction();
			if (!CollectionUtils.isEmpty(map))
				return session.createNativeQuery(sqlQueryString).setProperties(map).executeUpdate();
			else
				return session.createNativeQuery(sqlQueryString).executeUpdate();
		} catch (Exception ex) {
			throw new GenericDataException(String.format(EXCEPTION_S_OCCURED_DURING_QUERY_EXECUTION, ex));
		} finally {
			// clear DataSourceContext resources/cache so that it won't impact memory.
			DataSourceContextHolder.clearDataSourceContext();
		}
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Object findBySQLQuery(String sqlQueryString, int firstResult, int maxResults, Map<String, Object> map)
			throws GenericDataException {
		Query<?> sqlQuery = null;
		try (Session session = dataSourceComponent.getSession()) {
			session.beginTransaction();
			if (!CollectionUtils.isEmpty(map))
				sqlQuery = session.createNativeQuery(sqlQueryString).setProperties(map);
			else {
				sqlQuery = session.createNativeQuery(sqlQueryString);
			}
			if (firstResult >= 0) {
				sqlQuery.setFirstResult(firstResult);
			}
			if (maxResults > 0) {
				sqlQuery.setMaxResults(maxResults);
			}
			return sqlQuery.list();
		} catch (Exception ex) {
			throw new GenericDataException(String.format(EXCEPTION_S_OCCURED_DURING_QUERY_EXECUTION, ex));
		} finally {
			// clear DataSourceContext resources/cache so that it won't impact memory.
			DataSourceContextHolder.clearDataSourceContext();
		}
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Integer batchInsert(String sqlQueryString, List<Map<String, Object>> mapList, int batchSize)
			throws GenericDataException {
		try (Session session = dataSourceComponent.getSession()) {
			session.beginTransaction();
			for (int i = 0; i <= mapList.size(); i++) {
				session.createNativeQuery(sqlQueryString).setProperties(mapList.get(i)).executeUpdate();
				// if chunk==batchSize
				if (i > 0 && i % batchSize == 0) {
					// flush session object so that it won't impact memory
					session.flush();
					session.clear();
				}
			}
		} catch (Exception ex) {
			throw new GenericDataException(String.format(EXCEPTION_S_OCCURED_DURING_QUERY_EXECUTION, ex));
		} finally {
			// clear DataSourceContext resources/cache so that it won't impact memory.
			DataSourceContextHolder.clearDataSourceContext();
		}
		return mapList.size();
	}

	@Override
	public Long getUniqueId() {
		return sequenceGenerator.nextId();
	}

	@Override
	public Boolean addDatasource(DbProperty dbProperty) throws GenericDataException {
		// make sure no dataSource exists in connection pool with same name.
		if (dataSourceComponent.getAvailableDataSources().contains(dbProperty.getDataSourceName()))
			throw new GenericDataException(String.format("Datasource [%s] is already available in connection pool",
					dbProperty.getDataSourceName()));
		dataSourceComponent.addDataSource(dbProperty);
		return true;
	}

	@Override
	public Boolean updateDatasource(DbProperty dbProperty) throws GenericDataException {
		// make sure dataSource available in connection pool.
		if (!dataSourceComponent.getAvailableDataSources().contains(dbProperty.getDataSourceName()))
			throw new GenericDataException(String.format("Datasource [%s] is not available in connection pool",
					dbProperty.getDataSourceName()));
		dataSourceComponent.updateDataSource(dbProperty);
		return true;
	}

	@Override
	public Boolean deleteDatasource(String  dataSourceName) throws GenericDataException {
		// make sure dataSource available in connection pool.
		if (!dataSourceComponent.getAvailableDataSources().contains(dataSourceName))
			throw new GenericDataException(String.format("Datasource [%s] is not available in connection pool",
					dataSourceName));
		dataSourceComponent.deleteDataSource(dataSourceName);
		return true;
	}

}
