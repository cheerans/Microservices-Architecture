package com.ml.controller;

import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ml.core.impl.DecisionPredicate;
import com.ml.core.impl.DecisionTreeLogic;
import com.ml.core.impl.PrintTree;
import com.ml.dto.DataColumn;
import com.ml.dto.DataHolder;
import com.ml.dto.DataRow;
import com.ml.utils.ServiceStatus;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class MLController {
	
	
    /** Logger. */
    private static Logger log = Logger.getLogger(MLController.class);
    
    private String ZUULPROXYADDRESS = null;
    
    @Autowired
    Environment environment;
	
	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Autowired
	public MLController(Environment env) {
		super();
		ZUULPROXYADDRESS = env.getProperty("ZUULPROXYADDRESS");
	}

	@Autowired
	private RestTemplate restTemplate;
	
	private  RestTemplate plainRestTemplate = new RestTemplateBuilder().build();
	
	@RequestMapping(value="/health", method = RequestMethod.GET, produces = "application/json")	
	public @ResponseBody String health(HttpServletRequest request) throws Exception{	
		
		return "healthy" + ":" + request.getLocalPort();
	}
	
	
	@HystrixCommand(fallbackMethod = "getDecision4Postfallback")
	@RequestMapping(value="/getDecision4", method = RequestMethod.POST, produces = "application/json")	
	public @ResponseBody String getDecision4(HttpServletRequest request,@RequestBody String data) throws Exception{	
		
		return "getDecision4" + ":" + getServerCred(request);
	}
	
	@HystrixCommand(fallbackMethod = "getDecision4fallback")
	@RequestMapping(value="/getDecision4", method = RequestMethod.GET, produces = "application/json")	
	public @ResponseBody String getDecision4Get(HttpServletRequest request) throws Exception{	
		
		return "getDecision4" + ":" + getServerCred(request);
	}	
	
	@HystrixCommand(fallbackMethod = "getDecision3fallback")
	@RequestMapping(value="/getDecision3", method = RequestMethod.GET, produces = "application/json")	
	public @ResponseBody String getDecision3(HttpServletRequest request) throws Exception{	
		
		return "getDecision3" + ":" + getServerCred(request) + "-(W Zuul)" + plainRestTemplate.getForEntity(ZUULPROXYADDRESS + "/api-gateway/mlservice/getDecision4",String.class).getBody();
	}
   
	@HystrixCommand(fallbackMethod = "getDecision2fallback")
	@RequestMapping(value="/getDecision2", method = RequestMethod.GET, produces = "application/json")	
	public @ResponseBody String getDecision2(HttpServletRequest request) throws Exception{	
		
		return "getDecision2" + ":" + getServerCred(request) + "-(WOut Zuul)" + restTemplate.getForEntity("http://mlservice/getDecision3",String.class).getBody();
	}
    
	@HystrixCommand(fallbackMethod = "getDecision1fallback")
	@RequestMapping(value="/getDecision1", method = RequestMethod.GET, produces = "application/json")	
	public @ResponseBody String getDecision1(HttpServletRequest request) throws Exception{	
		
		return "getDecision1" + ":" + getServerCred(request) + "-(WOut Zuul)" + restTemplate.getForEntity("http://mlservice/getDecision2",String.class).getBody();
	}
	
	public @ResponseBody String getDecision4Postfallback(HttpServletRequest request,@RequestBody String data) throws Exception{	
		
		return ServiceStatus.buildFailure("getDecisionPost").toString();
	}
	
	public @ResponseBody String getDecision4fallback(HttpServletRequest request) throws Exception{	
		
		return ServiceStatus.buildFailure("getDecision4").toString();
	}
	
	public @ResponseBody String getDecision3fallback(HttpServletRequest request) throws Exception{	
		
		return ServiceStatus.buildFailure("getDecision3").toString();
	}
	
	public @ResponseBody String getDecision2fallback(HttpServletRequest request) throws Exception{	
		
		return ServiceStatus.buildFailure("getDecision2").toString();
	}
	
	public @ResponseBody String getDecision1fallback(HttpServletRequest request) throws Exception{	
		
		return ServiceStatus.buildFailure("getDecision1").toString();
	}
	
	public @ResponseBody String getDecisionfallback(@RequestBody String data,HttpServletRequest request) throws Exception{	
		
		return ServiceStatus.buildFailure("getDecision").toString();
	}
	
	@RequestMapping(value="/getDecision", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String getDecision(@RequestBody String data,HttpServletRequest request) throws Exception{		
				
		JSONObject json = new JSONObject(data);
		String fileData = new String(Base64.getDecoder().decode(json.getString("allLearningData")));
		String cols = new String(Base64.getDecoder().decode(json.getString("colsToLearnAbout")));
		String predictForData = new String(Base64.getDecoder().decode(json.getString("predictForData")));
		String pivotCol = new String(Base64.getDecoder().decode(json.getString("pivotCol")));
		String pivotValue = new String(Base64.getDecoder().decode(json.getString("pivotValue")));

		ObjectMapper mapper = new ObjectMapper();
        DataColumn pivotColObj = mapper.readValue(pivotCol, DataColumn.class);  
		List<DataRow> allLearningData = (List<DataRow>)mapper.readValue(fileData, new TypeReference<List<DataRow>>(){});
		List<DataColumn> colsToLearnAbout = mapper.readValue(cols, new TypeReference<List<DataColumn>>(){});
        DataRow predictForRow = new DataRow.Builder().setRowData(DecisionTreeLogic.parseData(predictForData,DecisionTreeLogic.TABDELIMITER)).build(colsToLearnAbout);               
        DecisionTreeLogic.learn(allLearningData, colsToLearnAbout, new DecisionPredicate(pivotValue,pivotColObj));
        if(log.isDebugEnabled()){
        	PrintTree.printTree(DataHolder.getInstance().getTree());	
        }
		Object decision = DecisionTreeLogic.makeDecision(predictForRow);
		return decision.toString();
	}

	// Total control - setup a model and return the view name yourself. Or
	// consider subclassing ExceptionHandlerExceptionResolver (see below).
	@ExceptionHandler(Exception.class)
	public ModelAndView handleError(HttpServletRequest req, Exception ex) {
	  
	    log.error("Request: " + req.getRequestURL() + " raised " + ex);
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", ex);
		mav.addObject("url", req.getRequestURL());
		mav.setViewName("error");
	    return mav;
	}
	
	private String getServerCred(HttpServletRequest request){

		StringBuffer cred = new StringBuffer();
		cred.append(environment.getProperty("spring.cloud.client.hostname"));
		cred.append("-");
		cred.append(environment.getProperty("spring.cloud.client.ip-address"));
		cred.append("-");	
		cred.append(request.getServerPort());			
		return cred.toString();
	}
}
