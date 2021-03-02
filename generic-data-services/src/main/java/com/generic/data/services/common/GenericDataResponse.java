package com.generic.data.services.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GenericDataResponse {

  private Object data;
  private String message;
  private Integer statusCode;
  
}
