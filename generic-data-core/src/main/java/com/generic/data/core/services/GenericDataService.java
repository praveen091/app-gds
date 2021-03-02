package com.generic.data.core.services;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.generic.data.core.common.GenericDataException;
import com.generic.data.core.dto.DbProperty;

public interface GenericDataService {
	
	/**
	 * Execute a query based on the given SQL query string.
	 * 
	 * @param sqlQueryString
	 *            a SQL query
	 *@param datasourceName
	 * @return a {@link List} containing 0 or more persistent instances
	 * @throws GenericDataException
	 */
	public Object findBySQLQuery(String sqlQueryString, Map<String, Object> map,String datasourceName) throws GenericDataException;

	/**
	 * Execute a query based on the given SQL query string.
	 * @param datasourceName
	 * @param sqlQueryString
	 *            a SQL query
	 * @param firstResult
	 *            the index of the first result object to be retrieved (numbered
	 *            from 0)
	 * @param maxResults
	 *            the maximum number of result objects to retrieve (or <=0 for no
	 *            limit)
	 * @return a {@link List} containing 0 or more persistent instances
	 * @throws GenericDataException
	 * 
	 */
	public Object findBySQLQuery(String sqlQueryString, final int firstResult, final int maxResults,
			Map<String, Object> map,String datasourceName) throws GenericDataException;

	/**
	 * Execute a query based on the given SQL query string to get count.
	 * @param datasourceName
	 * @param sqlQueryString
	 *            a SQL query
	 * @return count
	 * @throws DataAccessException
	 */
	public Integer countBySQLQuery(String sqlQueryString, Map<String, Object> map,String datasourceName) throws GenericDataException;

	/**
	 * Execute a query based on the given SQL insert query string
	 * @param datasourceName
	 * @param sqlQueryString
	 *            a SQL query
	 * @return
	 */
	public Integer insertBySQLQuery(String sqlQueryString, Map<String, Object> map,String datasourceName)
			throws GenericDataException;
	
	/**
	 * Execute a query based on the given SQL update query string
	 * @param datasourceName
	 * @param sqlQueryString
	 *            a SQL query
	 * @return
	 */
	public Integer updateBySQLQuery(String sqlQueryString, Map<String, Object> map,String datasourceName)
			throws GenericDataException;
	
	/**
	 * Execute a query based on the given SQL delete query string
	 * @param datasourceName
	 * @param sqlQueryString
	 *            a SQL query
	 * @return
	 */
	public Integer deleteBySQLQuery(String sqlQueryString, Map<String, Object> map,String datasourceName)
			throws GenericDataException;

	/**
	 * Execute batch insert based on the given SQL query string
	 * @param datasourceName
	 * @param sqlQueryString
	 *            a SQL query
	 * @return
	 */
	public Integer batchInsert(String sqlQueryString, List<Map<String, Object>> mapList, int batchSize,String datasourceName)
			throws GenericDataException;

	/**@param datasourceName
	 * Generate new sequence Id.
	 * @return
	 */
	public Long getUniqueId();
	
	/**
	 * add  new dataSource in connection pool.
	 * @return
	 */
	public boolean addDatasource(DbProperty dbProperty) throws GenericDataException;
	
	/**
	 * update existing dataSource in connection pool.
	 * @return
	 */
	public boolean updateDatasource(DbProperty dbProperty) throws GenericDataException;
	
	/**
	 * delete existing dataSource from connection pool.
	 * @return
	 */
	public boolean deleteDatasource(String  dataSourceName) throws GenericDataException;

}
