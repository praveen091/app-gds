package com.generic.data.services.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DbPropertyRequest {

	@ApiModelProperty(value = "url", example = "jdbc:postgresql://localhost:5432/dev_database", required = true)
	private String url;// mandatory
	@ApiModelProperty(value = "userName", example = "postgres", required = true)
	private String userName;// mandatory
	@ApiModelProperty(value = "passWord", example = "abc123", required = true)
	private String passWord;// mandatory
	@ApiModelProperty(value = "driverClassName", example = "org.postgresql.Driver", required = true)
	private String driverClassName;// mandatory
	@ApiModelProperty(value = "schemaName", example = "dev_schema", required = false)
	private String schemaName;// optional
	@ApiModelProperty(value = "datSourceName", example = "devDatasource", required = true)
	private String dataSourceName;// mandatory
	@ApiModelProperty(value = "dialect", example = "org.hibernate.dialect.PostgreSQL10Dialect", required = true)
	private String dialect;// mandatory
	@ApiModelProperty(value = "maxSize", example = "This property controls the maximum size that the pool is allowed to reach, including both idle and in-use connections.default is 10", required = false)
	private String maxSize;// optional
	@ApiModelProperty(value = "timeOut", example = "specify the timeout period  after which an idle connection is removed from the pool.default is 30000 sec", required = false)
	private String timeOut;// optional
	@ApiModelProperty(value = "allowFailover", example = "This property controls whether the pool can be suspended and resumed through JMX. This is useful for certain failover automation scenarios.default true", required = false)
	private boolean allowFailover;// optional
	@ApiModelProperty(value = "readOnly", example = "specify the read-only mode default false ", required = false)
	private boolean readOnly;// optional
	@ApiModelProperty(value = "maxLife", example = "This property controls the maximum lifetime of a connection in the pool.default is 1800000 sec", required = false)
	private String maxLife;// optional
}
