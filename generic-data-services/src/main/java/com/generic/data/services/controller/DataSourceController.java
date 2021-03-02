package com.generic.data.services.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.generic.data.services.common.GenericDataResponse;
import com.generic.data.services.request.DbPropertyRequest;
import com.generic.data.services.service.ApiService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * The {@link DataSourceController} class accept all the dataSource request sent
 * to generic data services .
 * 
 * All the CRUD services URL has been defined into this class
 * 
 * @author praveen.kumar
 *
 */
@RestController
@RequestMapping("/api-db/")
public class DataSourceController {
	private static final Logger logger = LogManager.getLogger(DataSourceController.class);
	@Autowired
	private ApiService service;

	@PostMapping(path = "add/datasource")
	@ApiOperation("Configure new datasource in Connection pool , status = 1 (success) , status = 3 (error)")
	public ResponseEntity<GenericDataResponse> addDataSource(@RequestBody DbPropertyRequest dbPropertyRequest,
			HttpServletRequest request) {
		logger.info("Request[{}] received for addDataSource", dbPropertyRequest);
		return new ResponseEntity<>(service.addDataSource(dbPropertyRequest), HttpStatus.OK);
	}

	@PutMapping(path = "update/datasource")
	@ApiOperation("update datasource in Connection pool , status = 1 (success) , status = 3 (error)")
	public ResponseEntity<GenericDataResponse> updateDataSource(@RequestBody DbPropertyRequest dbPropertyRequest,
			HttpServletRequest request) {
		logger.info("Request[{}] received for updateDataSource", dbPropertyRequest);
		return new ResponseEntity<>(service.updateDataSource(dbPropertyRequest), HttpStatus.OK);
	}

	@DeleteMapping(path = "delete/datasource")
	@ApiOperation("delete datasource from Connection pool , status = 1 (success) , status = 3 (error)")
	public ResponseEntity<GenericDataResponse> deleteDataSource(
			@RequestParam("dataSourceName") @ApiParam(value = "dataSourceName", required = true, name = "dataSourceName", example = "dataSource1") String dataSourceName,
			HttpServletRequest request) {
		logger.info("Request[{}] received for deleteDataSource", dataSourceName);
		return new ResponseEntity<>(service.deleteDataSource(dataSourceName), HttpStatus.OK);
	}

	@GetMapping(path = "sql/unique-id")
	@ApiOperation("Unique Id retrieve  action , status = 1 (success) , status = 3 (error)  ")
	public ResponseEntity<GenericDataResponse> getSequenceId() {
		return new ResponseEntity<>(service.getUniqueId(), HttpStatus.OK);
	}
}
