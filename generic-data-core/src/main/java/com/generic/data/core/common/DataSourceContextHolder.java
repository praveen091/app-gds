package com.generic.data.core.common;

/**
 * The {@link DataSourceContextHolder} class holds DataSourceName
 * for current context .
 * 
 * @author praveen.kumar
 *
 */
public class DataSourceContextHolder {
	private static final ThreadLocal<String> DATASOURCE_CONTEXT_HOLDER = new ThreadLocal<>();

	private DataSourceContextHolder() {
	}// do nothing

	/**
	 * Retrieve the dataSourceName associated to this context.
	 *
	 * @return
	 */
	public static String getDataSourceContext() {
		return DATASOURCE_CONTEXT_HOLDER.get();
	}

	public static void clearDataSourceContext() {
		DATASOURCE_CONTEXT_HOLDER.remove();
	}

	public static void setDataSourceName(String datasourceName) {
		clearDataSourceContext();
		DATASOURCE_CONTEXT_HOLDER.set(datasourceName);
	}

}
