package com.generic.data.core.config;

import org.hibernate.cfg.AvailableSettings;

public class DbPropertySetting {

	private DbPropertySetting() {
		// object creation not allowed.
	}

	public static final String HIKARI_CONNECTION_TIME_OUT = "hibernate.hikari.connectionTimeout";
	public static final String HIKARI_MAX_POOL_SIZE = "hibernate.hikari.maximumPoolSize";
	public static final String HIKARI_ALLOW_POOL_SUSPENSION = "hibernate.hikari.allowPoolSuspension";
	public static final String HIKARI_ONLY_FOR_READ = "hibernate.hikari.readOnly";
	public static final String HIKARI_MAX_IDLE_TIMEOUT = "hibernate.hikari.maximumPoolSize";
	public static final String HIKARI_MAX_LIFE_TIME = "hibernate.hikari.maxLifetime";
	public static final String HIKARI_POOL_NAME = "hibernate.hikari.poolName";
	public static final String HIKARI_CONNECTION_PROVIDER = "org.hibernate.hikaricp.internal.HikariCPConnectionProvider";
	public static final String THREAD = "thread";
	public static final String TRUE = "true";
	public static final String URL = AvailableSettings.URL;
	public static final String USER = AvailableSettings.USER;
	public static final String PASS = AvailableSettings.PASS;
	public static final String DRIVER = AvailableSettings.DRIVER;
	public static final String DIALECT = AvailableSettings.DIALECT;
	public static final String SHOW_SQL = AvailableSettings.SHOW_SQL;
	public static final String FORMAT_SQL = AvailableSettings.FORMAT_SQL;
	public static final String CURRENT_SESSION_CONTEXT_CLASS = AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS;
	public static final String CONNECTION_PROVIDER = AvailableSettings.CONNECTION_PROVIDER;
}
