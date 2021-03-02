package com.generic.data.core.dto;

import java.io.Serializable;

/**
 * 
 * @author praveen.kumar
 *
 */
public class DbProperty implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5865230682371912482L;
	/**
	 * JDBC URL.
	 */
	private String url;// mandatory
	/**
	 * Database userName.
	 */
	private String userName;// mandatory
	/**
	 * Database password.
	 */
	private String passWord;// mandatory
	/**
	 * driver class Name.
	 */
	private String driverClassName;// mandatory
	/**
	 * schema name
	 */
	private String schemaName;// optional
	/**
	 * Name of the datatSource.
	 */
	private String dataSourceName;// mandatory
	/**
	 * HiberNate dialect of database.
	 */
	private String dialect;// mandatory
	/**
	 * maximum size that the pool is allowed to reach.
	 */
	private String maxSize;// optional
	/**
	 * timeout period after which an idle connection is removed from the pool.
	 */
	private String timeOut;// optional
	/**
	 * This property controls the maximum lifetime of a connection in the pool.
	 */
	private String maxLifeTime;// optional
	/**
	 * This property controls whether the pool can be suspended and resumed through
	 * JMX.
	 */
	private boolean allowFailover;// optional
	/**
	 * specify the read-only mode default false.
	 */
	private boolean readOnly;// optional

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

	public String uniqueAttributes() {
		return dataSourceName;
	}

	public String getMaxSize() {
		return maxSize != null ? maxSize : "10";
	}

	public void setMaxSize(String maxSize) {
		this.maxSize = maxSize;
	}

	public String getTimeOut() {
		return timeOut != null ? timeOut : "30000";
	}

	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}

	public String getMaxLifeTime() {
		return maxLifeTime != null ? maxLifeTime : "1800000";
	}

	public void setMaxLifeTime(String maxLifeTime) {
		this.maxLifeTime = maxLifeTime;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public boolean isAllowFailover() {
		return allowFailover;
	}

	public void setAllowFailover(boolean allowFailover) {
		this.allowFailover = allowFailover;
	}

	@Override
	public String toString() {
		return "DbProperty [url=" + url + ", userName=" + userName + ", passWord=" + passWord + ", driverClassName="
				+ driverClassName + ", schemaName=" + schemaName + ", dataSourceName=" + dataSourceName + ", dialect="
				+ dialect + ", maxSize=" + maxSize + ", timeOut=" + timeOut + ", maxLifeTime=" + maxLifeTime
				+ ", allowFailover=" + allowFailover + ", readOnly=" + readOnly + "]";
	}

}
