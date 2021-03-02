package com.generic.data.services.request;

import java.util.Map;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaginationQueryRequest {

	@ApiModelProperty(value = "qyery", example = "select * from employee", required = true)
	private String query;
	@ApiModelProperty(value = "placeHolderValue", example = "{name:xyz}must not null for named query", required = false)
	private Map<String, Object> queryPlaceHolder;
	@ApiModelProperty(value = "offSet", example = "10", required = true)
	private Integer offSet;
	@ApiModelProperty(value = "maxResults", example = "10", required = true)
	private Integer maxResults;
	
}
