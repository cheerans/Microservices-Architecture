package com.ml.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


@Component
public class ServiceInterceptor implements HandlerInterceptor {
	
	// Define a counter metric for /prometheus
	static final Counter reqCounter = Metrics.counter("req.count");
	

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Object o) throws Exception {

		reqCounter.increment();
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Object o,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Object o, Exception e)
			throws Exception {

	}
}
