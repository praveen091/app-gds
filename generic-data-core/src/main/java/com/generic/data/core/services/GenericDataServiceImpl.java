package com.generic.data.core.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.generic.data.core.common.DataSourceContextHolder;
import com.generic.data.core.common.GenericDataException;
import com.generic.data.core.dao.GenericDao;
import com.generic.data.core.dto.DbProperty;

@Service
public class GenericDataServiceImpl implements GenericDataService {

	@Autowired
	private  GenericDao genericDao;

	@Override
	public Object findBySQLQuery(String sqlQueryString, Map<String, Object> map,String datasourceName) throws GenericDataException {
		DataSourceContextHolder.setDataSourceName(datasourceName);
		return genericDao.findBySQLQuery(sqlQueryString, map);
	}

	@Override
	public Object findBySQLQuery(String sqlQueryString, int firstResult, int maxResults, Map<String, Object> map,String datasourceName)
			throws GenericDataException {
		DataSourceContextHolder.setDataSourceName(datasourceName);
		return genericDao.findBySQLQuery(sqlQueryString, firstResult, maxResults, map);
	}

	@Override
	public Integer countBySQLQuery(String sqlQueryString, Map<String, Object> map,String datasourceName) throws GenericDataException {
		DataSourceContextHolder.setDataSourceName(datasourceName);
		return genericDao.countBySQLQuery(sqlQueryString, map);
	}

	@Override
	public Integer insertBySQLQuery(String sqlQueryString, Map<String, Object> map,String datasourceName) throws GenericDataException {
		DataSourceContextHolder.setDataSourceName(datasourceName);
		return genericDao.insertBySQLQuery(sqlQueryString, map);
	}

	@Override
	public Integer updateBySQLQuery(String sqlQueryString, Map<String, Object> map,String dataSource) throws GenericDataException {
		return genericDao.updateBySQLQuery(sqlQueryString, map);
	}

	@Override
	public Integer deleteBySQLQuery(String sqlQueryString, Map<String, Object> map,String datasourceName) throws GenericDataException {
		DataSourceContextHolder.setDataSourceName(datasourceName);
		return genericDao.deleteBySQLQuery(sqlQueryString, map);
	}

	@Override
	public Integer batchInsert(String sqlQueryString, List<Map<String, Object>> mapList, int batchSize,String datasourceName)
			throws GenericDataException {
		DataSourceContextHolder.setDataSourceName(datasourceName);
		return genericDao.batchInsert(sqlQueryString, mapList, batchSize);
	}

	@Override
	public Long getUniqueId() {
		return genericDao.getUniqueId();
	}

	@Override
	public boolean addDatasource(DbProperty dbProperty) throws GenericDataException {
		return genericDao.addDatasource(dbProperty);
	}

	@Override
	public boolean updateDatasource(DbProperty dbProperty) throws GenericDataException {
		return genericDao.updateDatasource(dbProperty);
	}

	@Override
	public boolean deleteDatasource(String  dataSourceName) throws GenericDataException {
		return genericDao.deleteDatasource(dataSourceName);
	}

}
