package com.ml.client;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APIGatewayController {
	
	@RequestMapping(value="/health", method = RequestMethod.GET, produces = "application/json")	
	public @ResponseBody String health(HttpServletRequest request) throws Exception{			
		return "healthy" + ":" + request.getLocalPort();
	}
}
