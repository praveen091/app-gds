package com.generic.data.core.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.generic.data.core.generator.SequenceGenerator;
/**
 * 
 * @author praveen.kumar
 *
 */
@Configuration
public class DataCoreConfig {

	private static final Logger logger = LogManager.getLogger(DataCoreConfig.class);
	@Value("${sequence.generator.nodeId:null}")
	private String nodeId;
	
	@Bean
	public SequenceGenerator getSequenceGenerator() {
		if(null!=nodeId && !"null".equalsIgnoreCase(nodeId) ) {
			return new SequenceGenerator(Integer.parseInt(nodeId));
		}
		logger.info("Node Id not found");
		logger.info("Value for [sequence.generator.nodeId] is empty.");
		logger.info("It is recommended to provide node Id per instance.");
		logger.info("Sequence Generator engine stated crating node Id...");
		return new SequenceGenerator();
	}
}
