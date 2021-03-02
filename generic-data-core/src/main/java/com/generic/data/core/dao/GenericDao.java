package com.generic.data.core.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.generic.data.core.common.GenericDataException;
import com.generic.data.core.dto.DbProperty;

/**
 * @author praveen.kumar {@link GenericDao} is Generic DAO (Data Access Object)
 *         with common methods to CRUD.
 * 
 */
public interface GenericDao {

	/**
	 * Execute a query based on the given SQL query string.
	 * 
	 * @param sqlQueryString a SQL query
	 * @return a {@link List} containing 0 or more persistent instances
	 * @throws GenericDataException
	 */
	public Object findBySQLQuery(String sqlQueryString, Map<String, Object> map) throws GenericDataException;

	/**
	 * Execute a query based on the given SQL query string.
	 * 
	 * @param sqlQueryString a SQL query
	 * @param firstResult    the index of the first result object to be retrieved
	 *                       (numbered from 0)
	 * @param maxResults     the maximum number of result objects to retrieve (or
	 *                       <=0 for no limit)
	 * @return a {@link List} containing 0 or more persistent instances
	 * @throws GenericDataException
	 * 
	 */
	public Object findBySQLQuery(String sqlQueryString, final int firstResult, final int maxResults,
			Map<String, Object> map) throws GenericDataException;

	/**
	 * Execute a query based on the given SQL query string to get count.
	 * 
	 * @param sqlQueryString a SQL query
	 * @return count
	 * @throws DataAccessException
	 */
	public Integer countBySQLQuery(String sqlQueryString, Map<String, Object> map) throws GenericDataException;

	/**
	 * Execute a query based on the given SQL insert query string
	 * 
	 * @param sqlQueryString a SQL query
	 * @return
	 */
	public Integer insertBySQLQuery(String sqlQueryString, Map<String, Object> map) throws GenericDataException;

	/**
	 * Execute a query based on the given SQL update query string
	 * 
	 * @param sqlQueryString a SQL query
	 * @return
	 */
	public Integer updateBySQLQuery(String sqlQueryString, Map<String, Object> map) throws GenericDataException;

	/**
	 * Execute a query based on the given SQL delete query string
	 * 
	 * @param sqlQueryString a SQL query
	 * @return
	 */
	public Integer deleteBySQLQuery(String sqlQueryString, Map<String, Object> map) throws GenericDataException;

	/**
	 * Execute batch insert based on the given SQL query string
	 * 
	 * @param sqlQueryString a SQL query
	 * @return
	 */
	public Integer batchInsert(String sqlQueryString, List<Map<String, Object>> mapList, int batchSize)
			throws GenericDataException;

	/**
	 * Generate new sequence Id.
	 * 
	 * @return
	 */
	public Long getUniqueId();

	/**
	 * add new dataSource in connection pool.
	 * 
	 * @return
	 */
	public Boolean addDatasource(DbProperty dbProperty) throws GenericDataException;

	/**
	 * update existing dataSource in connection pool.
	 * 
	 * @return
	 */
	public Boolean updateDatasource(DbProperty dbProperty) throws GenericDataException;

	/**
	 * delete existing dataSource from connection pool.
	 * 
	 * @return
	 */
	public Boolean deleteDatasource(String  dataSourceName) throws GenericDataException;

}
