package com.ml.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ml.core.impl.DecisionPredicate;
import com.ml.core.impl.DecisionTreeLogic;
import com.ml.dto.DataColumn;
import com.ml.dto.DataColumn.ValueTypeEnum;
import com.ml.dto.DataRow;

import junit.framework.Assert;

@Profile("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class MLMicroServiceTest{
	
	private static String predictForData = "9\tJohnson,Mrs. Oscar W\t19 Main Street, St. Loius, Missouri\tFemale\t27\tSt. Loius, Missouri\tAtlanta,GA\t11.1333\tSecond\t\t";
	
	static {
		
		predictForData = "4\tFutrelle,Mrs. Jacques Heath\t14,Main Street,St. Loius,Missouri\tFemale\t18\tSt. Loius, Missouri\tAtlanta,GA\t53.1\tSecond\t\t";
		predictForData = "1\tBraund,Mr. Owen Harris\t34,VandeRbilt,St.Loius,Missouri\tMale\t22\tSt.Loius,Missouri\tAtlanta,GA\t47.25\tFirst\t\t";
		predictForData = "2\tCumings,Mrs.John Bradley\t12,Main Street,St.Loius,Missouri\tMale\t38\tSt.Loius,Missouri\tAtlanta,GA\t17.2833\tSecond\t\t";
	}
	
	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
 
	@Autowired
	RestTemplate restTemplate;
	
	private  RestTemplate plainRestTemplate = new RestTemplateBuilder().build();
	
    
	@Before
	public void setUp() throws Exception {
		
		
	}

	@After
	public void tearDown() throws Exception {
		
	}    
	
    @Test
    public void invokeMicroserviceZullLoadBalanced() throws Exception{
    	
    	String res = plainRestTemplate.getForEntity("http://localhost:8091/api-gateway/mls/getDecision2",String.class).getBody();
    	Assert.assertNotNull("The method Ran", res);
    }
    
    @Test
    public void invokeMicroserviceAsLoadBalanced() throws Exception{
    	
    	String res = restTemplate.getForEntity("http://mls/getDecision2",String.class).getBody();
    	Assert.assertNotNull("The method Ran", res);
    }
    
    //@Test
    public void invokeMicroserviceAsRest() throws Exception{

    	JSONObject allMLData = createRequestObject(predictForData);    	
    	//rest is not finding the load balanced port    	
    	HttpHeaders httpHeaders = new HttpHeaders();
    	httpHeaders.set("Content-Type", "application/json");
    	HttpEntity <String> httpEntity = new HttpEntity <String> (allMLData.toString(), httpHeaders);    	
    	
    	//http://localhost:8091/MLLBProxy/getDecision
    	String decision = restTemplate.postForEntity(	"http://localhost:8091/api-gateway/mls/getDecision", 
														allMLData.toString(),
														String.class).getBody();
    	Assert.assertTrue("Result is - " + decision, 1 == 1);
    }    
    
    //Convert my file to a Base64 String
    public static JSONObject createRequestObject(String rowDataToPredictOn) throws IOException{

		String fileName = "data.csv";
		List<DataColumn> colsToLearnAbout = new ArrayList<DataColumn>();
		colsToLearnAbout.add(new DataColumn(4,"Age",ValueTypeEnum.INT_TYPE));
		colsToLearnAbout.add(new DataColumn(3,"Gender",ValueTypeEnum.STRING_TYPE));
		colsToLearnAbout.add(new DataColumn(7,"Fare",ValueTypeEnum.DOUBLE_TYPE));
		DataColumn pivotCol = new DataColumn(8,"Class",ValueTypeEnum.STRING_TYPE);
		String pivotValue = "First";
		
    	JSONObject allMLData = new JSONObject();
    	DecisionTreeLogic.readColumnIndexes(fileName, MLMicroServiceTest.class, colsToLearnAbout,new DecisionPredicate(pivotValue,pivotCol));
    	List<DataRow> rows = DecisionTreeLogic.readFile(fileName, MLMicroServiceTest.class, colsToLearnAbout);    	
    	ObjectMapper mapper = new ObjectMapper();
        try {
            allMLData.put("allLearningData",new String(Base64.getEncoder().encode(mapper.writeValueAsString(rows).getBytes())));
            String allrw = mapper.writeValueAsString(rows);
            allMLData.put("colsToLearnAbout",new String(Base64.getEncoder().encode(mapper.writeValueAsString(colsToLearnAbout).getBytes())));
            allMLData.put("pivotCol",new String(Base64.getEncoder().encode(mapper.writeValueAsString(pivotCol).getBytes())));
            allMLData.put("pivotValue",new String(Base64.getEncoder().encode(pivotValue.getBytes())));
            String rw = mapper.writeValueAsString(new DataRow.Builder().setRowData(DecisionTreeLogic.parseData(rowDataToPredictOn,DecisionTreeLogic.TABDELIMITER)).build(colsToLearnAbout));
            allMLData.put("predictForData",new String(Base64.getEncoder().encode(rowDataToPredictOn.getBytes())));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return allMLData;
    }
}