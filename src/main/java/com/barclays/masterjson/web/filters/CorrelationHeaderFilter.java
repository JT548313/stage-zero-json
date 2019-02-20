package com.barclays.masterjson.web.filters;

import com.barclays.masterjson.reporting.RequestCorrelation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.DispatcherType;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

/**
 * CorrelationHeaderFilter
 */
public class CorrelationHeaderFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CorrelationHeaderFilter.class);


    public void init(FilterConfig filterConfig) throws ServletException {
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String currentCorrId = httpServletRequest.getHeader(RequestCorrelation.CORRELATION_ID_HEADER);

        if (!currentRequestIsAsyncDispatcher(httpServletRequest)) {
            if (currentCorrId == null) {
                currentCorrId = UUID.randomUUID().toString();
                LOGGER.info("No correlationId found in Header. Generated : " + currentCorrId);
            } else {
                LOGGER.info("Found correlationId in Header : " + currentCorrId);
            }

            RequestCorrelation.setId(currentCorrId);
        }

        filterChain.doFilter(httpServletRequest, servletResponse);
    }


    @Override
    public void destroy() {
    }


    private boolean currentRequestIsAsyncDispatcher(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getDispatcherType().equals(DispatcherType.ASYNC);
    }
}