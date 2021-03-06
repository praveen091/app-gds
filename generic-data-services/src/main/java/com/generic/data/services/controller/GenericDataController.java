package com.generic.data.services.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.generic.data.services.common.GenericDataResponse;
import com.generic.data.services.filter.DataServiceFilter;
import com.generic.data.services.request.BatchQueryRequest;
import com.generic.data.services.request.QueryRequest;
import com.generic.data.services.service.ApiService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * The {@link GenericDataController} class accept all the request sent to
 * generic data services.
 * 
 * All the CRUD services URL has been defined into this class
 * 
 * @author praveen.kumar
 *
 */
@RestController
@RequestMapping("/api-data/")
public class GenericDataController {

	private static final Logger logger = LogManager.getLogger(GenericDataController.class);

	@Autowired
	private ApiService apiService;

	@GetMapping(path = "sql/find")
	@ApiOperation("Data retrieve  action , status = 1 (success) , status = 3 (error)")
	public ResponseEntity<GenericDataResponse> findBySQLQuery(
			@RequestParam("query") @ApiParam(value = "sql query", required = true, name = "query", example = "sql select query") String query,
			@RequestParam("queryPlaceHolder") @ApiParam(value = "placeHolderValue", required = false, name = "queryPlaceHolder", example = "{name:xyz}must not null for named query") Map<String, Object> queryPlaceHolder,
			@RequestHeader(DataServiceFilter.DATA_SOURCE_NAME) String dataSourceName, HttpServletRequest request) {
		logger.info(
				"Request received for findBySQLQuery with parameter query[{}] queryPlaceHolder[{}] datasourceName[{}]",
				query, queryPlaceHolder, dataSourceName);
		return new ResponseEntity<>(apiService.findBySQLQuery(query, queryPlaceHolder, dataSourceName), HttpStatus.OK);
	}

	@GetMapping(path = "sql/find/pagination")
	@ApiOperation("Data retrieve  action , status = 1 (success) , status = 3 (error)  ")
	public ResponseEntity<GenericDataResponse> findByPagination(
			@RequestParam("query") @ApiParam(value = "sql query", required = true, name = "query", example = "sql select query") String query,
			@RequestParam("queryPlaceHolder") @ApiParam(value = "placeHolderValue", required = false, name = "queryPlaceHolder", example = "{name:xyz}must not null for named query") Map<String, Object> queryPlaceHolder,
			@RequestParam("offSet") @ApiParam(value = "offSet", required = true, name = "offSet", example = "10") Integer offSet,
			@RequestParam("maxSize") @ApiParam(value = "maxSize", required = true, name = "maxSize", example = "20") Integer maxSize,
			@RequestHeader(DataServiceFilter.DATA_SOURCE_NAME) String dataSourceName, HttpServletRequest request) {
		logger.info(
				"Request received for findByPagination with parameter query[{}] queryPlaceHolder[{}] offset[{}] maxSize[{}] datasourceName[{}]",
				query, queryPlaceHolder, offSet, maxSize, dataSourceName);
		return new ResponseEntity<>(
				apiService.findBySqlPagination(query, queryPlaceHolder, offSet, maxSize, dataSourceName),
				HttpStatus.OK);
	}

	@GetMapping(path = "sql/count")
	@ApiOperation("Data retrieve  action , status = 1 (success) , status = 3 (error)")
	public ResponseEntity<GenericDataResponse> countBySQLQuery(
			@RequestParam("query") @ApiParam(value = "sql query", required = true, name = "query", example = "sql count query") String query,
			@RequestParam("queryPlaceHolder") @ApiParam(value = "placeHolderValue", required = false, name = "queryPlaceHolder", example = "{name:xyz}must not null for named query") Map<String, Object> queryPlaceHolder,
			@RequestHeader(DataServiceFilter.DATA_SOURCE_NAME) String dataSourceName, HttpServletRequest request) {
		logger.info(
				"Request received for countBySQLQuery with parameter query[{}] queryPlaceHolder[{}] datasourceName[{}]",
				query, queryPlaceHolder, dataSourceName);
		return new ResponseEntity<>(apiService.countBySQLQuery(query, queryPlaceHolder, dataSourceName), HttpStatus.OK);
	}

	@PostMapping(path = "sql/insert")
	@ApiOperation("Data insert  action , status = 1 (success) , status = 3 (error)")
	public ResponseEntity<GenericDataResponse> insertBySQLQuery(@RequestBody QueryRequest queryRequest,
			@RequestHeader(DataServiceFilter.DATA_SOURCE_NAME) String dataSourceName, HttpServletRequest request) {
		logger.info("Request[{}] received for insertBySQLQuery", queryRequest);
		return new ResponseEntity<>(apiService.insertBySQLQuery(queryRequest, dataSourceName), HttpStatus.OK);
	}

	@PutMapping(path = "sql/update")
	@ApiOperation("Data update  action , status = 1 (success) , status = 3 (error)")
	public ResponseEntity<GenericDataResponse> updateBySQLQuery(@RequestBody QueryRequest queryRequest,
			@RequestHeader(DataServiceFilter.DATA_SOURCE_NAME) String dataSourceName, HttpServletRequest request) {
		logger.info("Request[{}] received for updateBySQLQuery", queryRequest);
		return new ResponseEntity<>(apiService.updateBySQLQuery(queryRequest, dataSourceName), HttpStatus.OK);
	}

	@DeleteMapping(path = "sql/delete")
	@ApiOperation("Data delete  action , status = 1 (success) , status = 3 (error)")
	public ResponseEntity<GenericDataResponse> deleteBySQLQuery(@RequestBody QueryRequest queryRequest,
			@RequestHeader(DataServiceFilter.DATA_SOURCE_NAME) String dataSourceName, HttpServletRequest request) {
		logger.info("Request[{}] received for deleteBySQLQuery", queryRequest);
		return new ResponseEntity<>(apiService.deleteBySQLQuery(queryRequest, dataSourceName), HttpStatus.OK);
	}

	@PostMapping(path = "sql/batch")
	@ApiOperation("Batch insert  action , status = 1 (success) , status = 3 (error)")
	public ResponseEntity<GenericDataResponse> batchInsert(@RequestBody BatchQueryRequest queryRequest,
			@RequestHeader(DataServiceFilter.DATA_SOURCE_NAME) String dataSourceName, HttpServletRequest request) {
		logger.info("Request[{}] received for insertOrUpdateBySQLQuery", queryRequest);
		return new ResponseEntity<>(apiService.batchInsert(queryRequest, dataSourceName), HttpStatus.OK);
	}
}
