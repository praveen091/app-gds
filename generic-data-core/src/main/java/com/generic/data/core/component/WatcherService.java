package com.generic.data.core.component;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchService;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.generic.data.core.common.FileMonitorRunner;
import com.generic.data.core.common.GenericDataException;

/**
 * The {@link WatcherService} class to watch directory and to refresh spring
 * context in case any changes found in property file .
 * 
 * @author praveen.kumar32
 *
 */
@Configuration
public class WatcherService {
	private static final Logger LOGGER = LogManager.getLogger(WatcherService.class);

	@Autowired
	private DataSourceComponent dataSourceComponent;
	private String dir;
	private String fileName;

	@PostConstruct
	public void monitor() throws GenericDataException {
		appFileMonitor();
	}

	private void appFileMonitor() throws GenericDataException {
		try {
			dir = System.getProperty("generic.data.monitor.location");
			fileName = System.getProperty("generic.data.monitor.filename");
			if (isFileMonitorEnabled()) {
				Path path = Paths.get(dir);
				WatchService watchService = path.getFileSystem().newWatchService();
				path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_CREATE,
						StandardWatchEventKinds.ENTRY_DELETE);
				FileMonitorRunner runner = new FileMonitorRunner(watchService, dataSourceComponent, dir, fileName);
				Thread filemonitorThread = new Thread(runner);
				filemonitorThread.start();
			}
		} catch (NullPointerException | IllegalArgumentException e) {
			throw new GenericDataException(String.format(
					"error occured during refersh context for directory %s,posiblility directory location  value is empty",
					dir));
		} catch (IOException ioe) {
			LOGGER.error(ioe);
			throw new GenericDataException(String.format(
					"error while setting refersh context for directory %s,Possibility file location could be wrong",
					dir));
		}
	}

	private boolean isFileMonitorEnabled() {
		if (StringUtils.isEmpty(dir) || StringUtils.isEmpty(fileName)) {
			LOGGER.info("found empty value for [generic.data.monitor.location] or [generic.data.monitor.filename]");
			LOGGER.warn("Property file auto refresh operation will be disable");
			return false;
		}
		return true;

	}
}
