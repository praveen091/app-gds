package com.generic.data.services.request;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 
 * @author praveen.kumar
 *
 */
@ToString
@Data
public class BatchQueryRequest {

	@ApiModelProperty(value = "qyery", example = "sql insert query", required = true)
	private String query;
	@ApiModelProperty(value = "placeHolderValue", example = "{name:xyz}", required = true)
	private List<Map<String, Object>> queryPlaceHolder;
	@ApiModelProperty(value = "batchSize", example = "between 10-50", required = true)
	private Integer batchSize;
	
}
