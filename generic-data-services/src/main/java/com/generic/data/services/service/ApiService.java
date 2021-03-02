package com.generic.data.services.service;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.generic.data.core.dto.DbProperty;
import com.generic.data.core.services.GenericDataService;
import com.generic.data.services.common.GenericDataResponse;
import com.generic.data.services.request.BatchQueryRequest;
import com.generic.data.services.request.DbPropertyRequest;
import com.generic.data.services.request.QueryRequest;

/**
 * 
 * @author praveen.kumar {@link ApiService} is service class which handles
 *         business logic.
 *
 */
@Service
public class ApiService {
	private static final String FAILED = "Failed";
	private static final String SUCCESS = "success";
	private static final String RESPONSE_SENT_FOR_REQUEST = "Response[{}] sent for request[{}]";
	private static final Logger logger = LogManager.getLogger(ApiService.class);
	@Autowired
	private GenericDataService genericDataService;

	/**
	 * 
	 * @param sqlQueryString
	 * @param map
	 * @return
	 */
	public GenericDataResponse countBySQLQuery(String query, Map<String, Object> queryPlaceHolder,
			String dataSourceName) {
		GenericDataResponse response = new GenericDataResponse();
		try {
			response.setData(genericDataService.countBySQLQuery(query, queryPlaceHolder, dataSourceName));
			response.setMessage(SUCCESS);
			response.setStatusCode(1);
		} catch (DataAccessException dae) {
			logger.error(dae.getMessage());
			response.setMessage(dae.getMessage());
			response.setStatusCode(3);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			response.setMessage(ex.getMessage());
			response.setStatusCode(3);
		}
		logger.info(RESPONSE_SENT_FOR_REQUEST, response, query);
		return response;

	}

	/**
	 * 
	 * @param sqlQueryString
	 * @param map
	 * @return
	 */
	public GenericDataResponse insertBySQLQuery(QueryRequest queryRequest, String dataSourceName) {
		GenericDataResponse response = new GenericDataResponse();
		try {
			response.setData(genericDataService.insertBySQLQuery(queryRequest.getQuery(),
					queryRequest.getQueryPlaceHolder(), dataSourceName));
			response.setMessage(SUCCESS);
			response.setStatusCode(1);
		} catch (DataAccessException dae) {
			logger.error(dae.getMessage());
			response.setMessage(dae.getMessage());
			response.setStatusCode(3);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			response.setMessage(ex.getMessage());
			response.setStatusCode(3);
		}
		logger.info(RESPONSE_SENT_FOR_REQUEST, response, queryRequest);
		return response;

	}

	/**
	 * 
	 * @param sqlQueryString
	 * @param map
	 * @return
	 */
	public GenericDataResponse updateBySQLQuery(QueryRequest queryRequest, String dataSourceName) {
		GenericDataResponse response = new GenericDataResponse();
		try {
			response.setData(genericDataService.updateBySQLQuery(queryRequest.getQuery(),
					queryRequest.getQueryPlaceHolder(), dataSourceName));
			response.setMessage(SUCCESS);
			response.setStatusCode(1);
		} catch (DataAccessException dae) {
			logger.error(dae.getMessage());
			response.setMessage(dae.getMessage());
			response.setStatusCode(3);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			response.setMessage(ex.getMessage());
			response.setStatusCode(3);
		}
		logger.info(RESPONSE_SENT_FOR_REQUEST, response, queryRequest);
		return response;
	}

	/**
	 * 
	 * @param sqlQueryString
	 * @param map
	 * @return
	 */
	public GenericDataResponse deleteBySQLQuery(QueryRequest queryRequest, String dataSourceName) {
		GenericDataResponse response = new GenericDataResponse();
		try {
			response.setData(genericDataService.deleteBySQLQuery(queryRequest.getQuery(),
					queryRequest.getQueryPlaceHolder(), dataSourceName));
			response.setMessage(SUCCESS);
			response.setStatusCode(1);
		} catch (DataAccessException dae) {
			logger.error(dae.getMessage());
			response.setMessage(dae.getMessage());
			response.setStatusCode(3);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			response.setMessage(ex.getMessage());
			response.setStatusCode(3);
		}
		logger.info(RESPONSE_SENT_FOR_REQUEST, response, queryRequest);
		return response;
	}

	/**
	 * 
	 * @param sqlQueryString
	 * @param map
	 * @return
	 */
	public GenericDataResponse findBySQLQuery(String query, Map<String, Object> queryPlaceHolder,
			String dataSourceName) {
		GenericDataResponse response = new GenericDataResponse();
		try {
			response.setData(genericDataService.findBySQLQuery(query, queryPlaceHolder, dataSourceName));
			response.setMessage(SUCCESS);
			response.setStatusCode(1);
		} catch (DataAccessException dae) {
			logger.error(dae.getMessage());
			response.setMessage(dae.getMessage());
			response.setStatusCode(3);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			response.setMessage(ex.getMessage());
			response.setStatusCode(3);
		}
		logger.info(RESPONSE_SENT_FOR_REQUEST, response, query);
		return response;

	}

	/**
	 * 
	 * @param sqlQueryString
	 * @param firstResult
	 * @param maxResults
	 * @param map
	 * @return
	 */
	public GenericDataResponse findBySqlPagination(String query, Map<String, Object> queryPlaceHolder, Integer offSet,
			Integer maxSize, String dataSourceName) {
		GenericDataResponse response = new GenericDataResponse();
		try {
			response.setData(
					genericDataService.findBySQLQuery(query, offSet, maxSize, queryPlaceHolder, dataSourceName));
			response.setMessage(SUCCESS);
			response.setStatusCode(1);
		} catch (DataAccessException dae) {
			logger.error(dae.getMessage());
			response.setMessage(dae.getMessage());
			response.setStatusCode(3);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			response.setMessage(ex.getMessage());
			response.setStatusCode(3);
		}
		logger.info(RESPONSE_SENT_FOR_REQUEST, response, query);
		return response;

	}

	/**
	 * 
	 * @param queryRequest
	 * @return
	 */
	public GenericDataResponse batchInsert(BatchQueryRequest queryRequest, String dataSourceName) {
		GenericDataResponse response = new GenericDataResponse();
		try {
			response.setData(genericDataService.batchInsert(queryRequest.getQuery(), queryRequest.getQueryPlaceHolder(),
					queryRequest.getBatchSize(), dataSourceName));
			response.setMessage(SUCCESS);
			response.setStatusCode(1);
		} catch (DataAccessException dae) {
			logger.error(dae.getMessage());
			response.setMessage(dae.getMessage());
			response.setStatusCode(3);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			response.setMessage(ex.getMessage());
			response.setStatusCode(3);
		}
		logger.info(RESPONSE_SENT_FOR_REQUEST, response, queryRequest);
		return response;
	}

	/**
	 * 
	 * @return
	 */
	public GenericDataResponse getUniqueId() {
		GenericDataResponse response = new GenericDataResponse();
		try {
			response.setData(genericDataService.getUniqueId());
			response.setMessage(SUCCESS);
			response.setStatusCode(1);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			response.setMessage(ex.getMessage());
			response.setStatusCode(3);
		}
		logger.info("Response[{}] sent with uniqueId", response);
		return response;

	}

	/**
	 * 
	 * @param dbPropertyRequest
	 * @return
	 */
	public GenericDataResponse addDataSource(DbPropertyRequest dbPropertyRequest) {
		GenericDataResponse response = new GenericDataResponse();
		try {
			DbProperty dbProperty = new DbProperty();
			dbProperty.setDataSourceName(dbPropertyRequest.getDataSourceName());
			dbProperty.setDialect(dbPropertyRequest.getDialect());
			dbProperty.setDriverClassName(dbPropertyRequest.getDriverClassName());
			dbProperty.setMaxSize(!StringUtils.isEmpty(dbPropertyRequest.getMaxSize()) ? dbPropertyRequest.getMaxSize()
					: dbProperty.getMaxSize());
			dbProperty.setPassWord(dbPropertyRequest.getPassWord());
			dbProperty.setSchemaName(
					!StringUtils.isEmpty(dbPropertyRequest.getSchemaName()) ? dbPropertyRequest.getSchemaName()
							: "");
			dbProperty.setTimeOut(!StringUtils.isEmpty(dbPropertyRequest.getTimeOut()) ? dbPropertyRequest.getTimeOut()
					: dbProperty.getTimeOut());
			dbProperty.setMaxLifeTime(
					!StringUtils.isEmpty(dbPropertyRequest.getMaxLife()) ? dbPropertyRequest.getMaxLife()
							: dbProperty.getMaxLifeTime());
			dbProperty.setAllowFailover(dbPropertyRequest.isAllowFailover());
			dbProperty.setReadOnly(dbPropertyRequest.isReadOnly());
			dbProperty.setUrl(dbPropertyRequest.getUrl());
			dbProperty.setUserName(dbPropertyRequest.getUserName());
			if (genericDataService.addDatasource(dbProperty)) {
				response.setMessage(SUCCESS);
				response.setStatusCode(1);
				return response;
			}
			response.setMessage(FAILED);
			response.setStatusCode(3);
			return response;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			response.setMessage(ex.getMessage());
			response.setStatusCode(3);
		}
		return response;

	}

	/**
	 * 
	 * @param dbPropertyRequest
	 * @return
	 */

	public GenericDataResponse updateDataSource(DbPropertyRequest dbPropertyRequest) {
		GenericDataResponse response = new GenericDataResponse();
		try {
			DbProperty dbProperty = new DbProperty();
			dbProperty.setDataSourceName(dbPropertyRequest.getDataSourceName());
			dbProperty.setDialect(dbPropertyRequest.getDialect());
			dbProperty.setDriverClassName(dbPropertyRequest.getDriverClassName());
			dbProperty.setMaxSize(!StringUtils.isEmpty(dbPropertyRequest.getMaxSize()) ? dbPropertyRequest.getMaxSize()
					: dbProperty.getMaxSize());
			dbProperty.setPassWord(dbPropertyRequest.getPassWord());
			dbProperty.setSchemaName(
					!StringUtils.isEmpty(dbPropertyRequest.getSchemaName()) ? dbPropertyRequest.getSchemaName()
							: "");
			dbProperty.setTimeOut(!StringUtils.isEmpty(dbPropertyRequest.getTimeOut()) ? dbPropertyRequest.getTimeOut()
					: dbProperty.getTimeOut());
			dbProperty.setMaxLifeTime(
					!StringUtils.isEmpty(dbPropertyRequest.getMaxLife()) ? dbPropertyRequest.getMaxLife()
							: dbProperty.getMaxLifeTime());
			dbProperty.setAllowFailover(dbPropertyRequest.isAllowFailover());
			dbProperty.setReadOnly(dbPropertyRequest.isReadOnly());
			dbProperty.setUrl(dbPropertyRequest.getUrl());
			dbProperty.setUserName(dbPropertyRequest.getUserName());
			if (genericDataService.updateDatasource(dbProperty)) {
				response.setMessage(SUCCESS);
				response.setStatusCode(1);
				return response;
			}
			response.setMessage(FAILED);
			response.setStatusCode(3);
			return response;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			response.setMessage(ex.getMessage());
			response.setStatusCode(3);
		}
		return response;

	}

	/**
	 * 
	 * @param dbPropertyRequest
	 * @return
	 */
	public GenericDataResponse deleteDataSource(String dataSourceName) {
		GenericDataResponse response = new GenericDataResponse();
		try {
			if (genericDataService.deleteDatasource(dataSourceName)) {
				response.setMessage(SUCCESS);
				response.setStatusCode(1);
				return response;
			}
			response.setMessage(FAILED);
			response.setStatusCode(3);
			return response;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			response.setMessage(ex.getMessage());
			response.setStatusCode(3);
		}
		return response;

	}
}
