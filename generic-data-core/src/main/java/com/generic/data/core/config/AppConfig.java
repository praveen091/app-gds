package com.generic.data.core.config;

import java.io.Serializable;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.generic.data.core.dto.DbProperty;


/**
 * The {@link AppConfig} class holds all the database details available in  properties file  .
 * 
 * @author praveen.kumar32
 *
 */

@Configuration
@ConfigurationProperties("db") 
public class AppConfig implements Serializable{
/**
	 * 
	 */
private static final long serialVersionUID = -1066153647875958574L;
private List<DbProperty> config;

public List<DbProperty> getConfig() {
	return config;
}

public void setConfig(List<DbProperty> config) {
	this.config = config;
}

}
