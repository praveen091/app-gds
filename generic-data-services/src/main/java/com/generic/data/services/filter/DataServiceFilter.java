package com.generic.data.services.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.generic.data.core.common.DataSourceContextHolder;

/**
 * The {@link DataServiceFilter} makes sure that the dataSource context has been
 * decided and set based on DATA_SOURCE_NAME present in request headers - if the
 * user hasn't sent DATA_SOURCE_NAME it kicks the request out.
 *
 * @author praveen.kumar
 */
@Component
@WebFilter(urlPatterns = { "/api-data*" }, filterName = "DataServiceFilter", asyncSupported = true)
public class DataServiceFilter implements Filter {

	private static final String API_DATA = "api-data";

	public static final String DATA_SOURCE_NAME = "X-DATASOURCE";

	private static final String INVALID_HEADERS = "Invalid Headers";

	private static final Logger LOGGER = LogManager.getLogger(DataServiceFilter.class);

	@Override
	public void destroy() {
		/** do nothing at shut down */
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		/** initialization to be done by spring */
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// We implicitly cast to HttpServletRequest, because if it's an instance of
		// another class we are
		// in trouble - so either way we want to throw a error 500
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		String url = getUrl(httpServletRequest);
		if(!API_DATA.contains(url)) {
			chain.doFilter(httpServletRequest, httpServletResponse);
			return;
		}
		String headers = getHeadersAsString(httpServletRequest);
		if (validHeaderIsPresent(httpServletRequest)) {
			LOGGER.printf(Level.TRACE, "url [%s] has valid headers", url);
			chain.doFilter(httpServletRequest, httpServletResponse);
			//got response
			//clear DataSourceContext resources/cache  so that it won't impact memory.
			DataSourceContextHolder.clearDataSourceContext();
		
		} else {
			LOGGER.printf(Level.TRACE, "For request URL %s%nHeaders present is invalid:%n%s", url, headers);
			LOGGER.printf(Level.TRACE, "No further action for  request URL[%d] ", url);
			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.setContentType(INVALID_HEADERS);

		}
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	private String getHeadersAsString(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		Enumeration<String> headers = request.getHeaderNames();
		for (String header : Collections.list(headers)) {
			String value = request.getHeader(header);
			sb.append(String.format("  %s:%s%n", header, value));
		}
		return sb.toString();
	}

	/**
	 * 
	 * @param httpServletRequest
	 * @return
	 */
	private String getUrl(HttpServletRequest httpServletRequest) {
		String url = httpServletRequest.getRequestURL().toString();
		String queryString = httpServletRequest.getQueryString();
		String query = queryString == null ? "" : String.format("?%s", queryString);
		return String.format("%s%s", url, query);
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	private boolean validHeaderIsPresent(HttpServletRequest request) {
		String value = request.getHeader(DATA_SOURCE_NAME);
		return !StringUtils.isEmpty(value);
	}

}
