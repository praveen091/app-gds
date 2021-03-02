package com.generic.data.core.common;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;

import com.generic.data.core.component.DataSourceComponent;
import com.generic.data.core.config.AppConfig;

/**
 * The {@link FileMonitorRunner} class to handle refresh/re-connect
 * DataSource connection as on when any change in DB property.
 * 
 * @author praveen.kumar
 *
 */
public class FileMonitorRunner implements Runnable {
	private static final Logger LOGGER = LogManager.getLogger(FileMonitorRunner.class);
	private WatchService watchService;
	private DataSourceComponent dataSourceComponent;
	private String dir;
	private String nameOfFile;

	public FileMonitorRunner(WatchService watchService, DataSourceComponent dataSourceComponent, String dir,
			String nameOfFile) {
		this.watchService = watchService;
		this.dataSourceComponent = dataSourceComponent;
		this.dir = dir;
		this.nameOfFile = nameOfFile;
	}

	@Override
	public void run() {
		try {
			while (true) {
				WatchKey key = getWatchKey(watchService);
				if (null != key) {
					for (WatchEvent<?> event : key.pollEvents()) {
						triggerRefershContext(event);
					}
					key.reset();
				}
			}
		} catch (Exception ex) {
			LOGGER.error("Exception occured during file monitor opertion");
		}

	}

	/**
	 * 
	 * @param watchService
	 * @return
	 */
	private WatchKey getWatchKey(WatchService watchService) {
		try {
			// wait for a key to be available
			return watchService.take();
		} catch (InterruptedException ex) {
			LOGGER.warn(ex);
			// Restore interrupted state...
			Thread.currentThread().interrupt();
			return null;
		}
	}

	/**
	 * 
	 * @param event
	 */
	private void triggerRefershContext(WatchEvent<?> event) {

		if (null != event && (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY
				|| event.kind() == StandardWatchEventKinds.ENTRY_CREATE
				|| event.kind() == StandardWatchEventKinds.ENTRY_DELETE)) {
			LOGGER.info("Action [{}] has been performed on file[{}]", event.kind(), event.context());
			if (event.context().toString().equals(nameOfFile)) {
				LOGGER.info("Changes found in [{}]", nameOfFile);
				LOGGER.info("In Queue of reloading property in bean context started");
				try {
					AppConfig config = appConfigProperties();
					if (config == null) {
						LOGGER.info("Error during re-loading file[{}] ,Refresh process skipped", nameOfFile);
						return;
					}
					dataSourceComponent.setDbConfig(config);
					dataSourceComponent.refreshDatasource(dataSourceComponent.getDbConfig());
				} catch (GenericDataException e) {
					LOGGER.error(e);
				}
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	private AppConfig appConfigProperties() {
		try (InputStream input = new FileInputStream(dir + nameOfFile)) {
			LOGGER.info("Lookup for propertyName with prefix[db] started in property file [{}]", nameOfFile);
			Properties props = new Properties();
			props.load(input);
			ConfigurationPropertySource propertySource = new MapConfigurationPropertySource(props);
			Binder binder = new Binder(propertySource);
			return binder.bind("db", AppConfig.class).get();
		} catch (Exception e) {
			LOGGER.error(e);
		}
		return null;
	}

}
